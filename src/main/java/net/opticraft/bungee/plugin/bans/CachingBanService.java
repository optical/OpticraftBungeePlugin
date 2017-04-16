package net.opticraft.bungee.plugin.bans;

import net.opticraft.bungee.plugin.util.concurrency.ResultTask;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

// Implements basic cache with automatic lazy expiry. Calls to check banned status and get ban history are cached
public class CachingBanService<TBanKey> implements BanService<TBanKey> {
	private final BanService actualBanService;
	private final Duration cacheEntryLifetime;
	private final ConcurrentHashMap<TBanKey, CachedEntry<ResultTask<BanLookupResult<TBanKey>>>> fullBanLookupCache;
	private final ConcurrentHashMap<TBanKey, CachedEntry<ResultTask<Instant>>> banExpiryLookupCache;

	public CachingBanService(BanService actualBanService, Duration cacheEntryLifetime) {
		this.actualBanService = actualBanService;
		this.cacheEntryLifetime = cacheEntryLifetime;
		this.fullBanLookupCache = new ConcurrentHashMap<>();
		this.banExpiryLookupCache = new ConcurrentHashMap<>();
	}

	// We populate the entire cache up front to try reduce the impact of restarts and the stampede that may follow
	public void initializeUnbanCache() throws ExecutionException, InterruptedException {
		ResultTask<ArrayList<SimpleBanDetails>> allActiveBansFuture = actualBanService.getAllActiveBans();
		// TODO: Fix this up once a blocking wait() is available on tasks
		/*		ArrayList<SimpleBanDetails> allActiveBans = allActiveBansFuture.get();
		Instant expiryTime = calculateExpiryTime();

		for (SimpleBanDetails<TBanKey> activeBan : allActiveBans) {
			banExpiryLookupCache.put(activeBan.id, new CachedEntry<>(CompletableFuture.completedFuture(activeBan.expiryDate), expiryTime));
		}*/
	}

	@Override
	public ResultTask<BanLookupResult<TBanKey>> getFullBanDetails(TBanKey id) {
		return getCachedEntry(id, fullBanLookupCache, () -> actualBanService.getFullBanDetails(id));
	}

	@Override
	public ResultTask<Instant> getUnbanDate(TBanKey id) {
		return getCachedEntry(id, banExpiryLookupCache, () -> actualBanService.getUnbanDate(id));
	}

	@Override
	public ResultTask<Boolean> unban(TBanKey id, UUID unbannedId, String unbanReason) {
		// We immediately invalidate this cache entry regardless of success/fail of the unban
		fullBanLookupCache.remove(id);
		banExpiryLookupCache.remove(id);
		return actualBanService.unban(id, unbannedId, unbanReason);
	}

	@Override
	public ResultTask<Boolean> ban(TBanKey id, UUID bannerId, String banReason, Instant expiryDate) {
		// Invalidate the cache
		fullBanLookupCache.remove(id);
		banExpiryLookupCache.remove(id);
		return actualBanService.ban(id, bannerId, banReason, expiryDate);
	}

	@Override
	public ResultTask<ArrayList<SimpleBanDetails>> getAllActiveBans() {
		return actualBanService.getAllActiveBans();
	}

	private <TCacheItem> TCacheItem getCachedEntry(TBanKey id, ConcurrentHashMap<TBanKey, CachedEntry<TCacheItem>> cache, Supplier<TCacheItem> itemProducer) {
		CachedEntry<TCacheItem> cacheResult = cache.get(id);
		if (cacheResult != null || cacheResult.hasExpired()) {
			// remove the entry atomically
			fullBanLookupCache.remove(id, cacheResult);
			cacheResult = null;
		}

		if (cacheResult == null) {
			// Atomically add to the cache - should be no chance of a race condition here
			cacheResult = cache.computeIfAbsent(id, key -> {
				// Defer to non caching implementation
				TCacheItem cacheItemResult = itemProducer.get();
				return new CachedEntry<>(cacheItemResult, calculateExpiryTime());
			});
		}
		return cacheResult.cacheItem;
	}

	private Instant calculateExpiryTime() {
		return Instant.now().plus(cacheEntryLifetime.toMillis(), ChronoUnit.MILLIS);
	}
}
