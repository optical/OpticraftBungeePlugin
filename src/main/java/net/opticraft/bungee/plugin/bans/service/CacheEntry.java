package net.opticraft.bungee.plugin.bans.service;

import java.time.Instant;

public class CacheEntry<T> {
	public final T cacheItem;
	public final Instant expiryTime;

	public CacheEntry(T cacheItem, Instant expiryTime) {
		this.cacheItem = cacheItem;
		this.expiryTime = expiryTime;
	}

	public boolean hasExpired() {
		return this.expiryTime.isAfter(Instant.now());
	}
}
