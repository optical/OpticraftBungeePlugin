package net.opticraft.bungee.plugin.bans.service;

import java.time.Duration;

public class BanComponentConfiguration {
	public final boolean shouldCache;
	public final Duration cacheDuration;
	public final String banMessage;

	public BanComponentConfiguration(boolean shouldCache, Duration cacheDuration, String banMessage) {
		this.shouldCache = shouldCache;
		this.cacheDuration = cacheDuration;
		this.banMessage = banMessage;
	}
}
