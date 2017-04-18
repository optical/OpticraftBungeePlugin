package net.opticraft.bungee.plugin.bans.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CacheEntry<T> {
	public final T cacheItem;
	public final Instant expiryTime;

	public CacheEntry(T cacheItem, Instant expiryTime) {
		this.cacheItem = cacheItem;
		this.expiryTime = expiryTime;
	}

	public CacheEntry(T cacheItem, Duration cacheDuration) {
		this(cacheItem, Instant.now().plus(cacheDuration.toMillis(), ChronoUnit.MILLIS));
	}

	public boolean hasExpired() {
		return this.expiryTime.isAfter(Instant.now());
	}
}
