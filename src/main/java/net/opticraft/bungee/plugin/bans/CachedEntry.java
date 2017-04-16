package net.opticraft.bungee.plugin.bans;

import java.time.Instant;

public class CachedEntry<T> {
	public final T cacheItem;
	public final Instant expiryTime;

	public CachedEntry(T cacheItem, Instant expiryTime) {
		this.cacheItem = cacheItem;
		this.expiryTime = expiryTime;
	}

	public boolean hasExpired() {
		return this.expiryTime.isAfter(Instant.now());
	}
}
