package net.opticraft.bungee.plugin.uuid;

import net.opticraft.bungee.plugin.bans.service.CacheEntry;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

public class CachingUuidService implements UuidService {
	private final UuidService actualService;
	private final Duration cacheDuration;
	private final ConcurrentHashMap<String, CacheEntry<CompletionStage<UUID>>> uuidCache;

	public CachingUuidService(UuidService actualService, Duration cacheDuration) {
		this.actualService = actualService;
		this.cacheDuration = cacheDuration;
		this.uuidCache = new ConcurrentHashMap<>();
	}

	@Override
	public CompletionStage<UUID> getUniqueId(String username) {
		String lowercaseUsername = username.toLowerCase();
		CacheEntry<CompletionStage<UUID>> cachedEntry = uuidCache.get(lowercaseUsername);
		if (cachedEntry != null) {
			if (!cachedEntry.hasExpired()) {
				return cachedEntry.cacheItem;
			} else {
				uuidCache.remove(lowercaseUsername, cachedEntry);
			}
		}

		return uuidCache.computeIfAbsent(username, key -> new CacheEntry<>(actualService.getUniqueId(username), cacheDuration)).cacheItem;
	}
}
