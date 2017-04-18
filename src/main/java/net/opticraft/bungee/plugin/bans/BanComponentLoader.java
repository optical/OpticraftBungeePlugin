package net.opticraft.bungee.plugin.bans;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.opticraft.bungee.plugin.bans.service.BanComponentConfiguration;
import net.opticraft.bungee.plugin.core.OpticraftBungeeComponentLoader;
import net.opticraft.bungee.plugin.uuid.UuidService;
import net.opticraft.bungee.plugin.util.ConfigHelper;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class BanComponentLoader implements OpticraftBungeeComponentLoader<BanComponent> {
	public static final String BANS_CACHE_DURATION_SECONDS_KEY = "bans.cache.durationSeconds";
	public static final String BANS_CACHE_ENABLED_KEY = "bans.cache.enabled";
	public static final String BANS_ENABLED_KEY = "bans.enabled";
	private final Plugin plugin;
	private final UuidService uuidService;

	private boolean shouldEnable;
	private boolean shouldCache;
	private int cacheExpirySeconds;

	public BanComponentLoader(Plugin plugin, UuidService uuidService) {
		this.plugin = plugin;
		this.uuidService = uuidService;
	}

	@Override
	public void initializeConfig(Configuration configuration) {
		ConfigHelper.addConfigDefault(configuration, BANS_ENABLED_KEY, false);
		ConfigHelper.addConfigDefault(configuration, BANS_CACHE_ENABLED_KEY, true);
		// TODO: Collect statistics on cache/hit miss.
		ConfigHelper.addConfigDefault(configuration, BANS_CACHE_DURATION_SECONDS_KEY, 180);
	}

	@Override
	public void readConfig(Configuration configuration) {
		shouldEnable = configuration.getBoolean("bans.enabled");
		shouldCache = configuration.getBoolean("bans.cache.enabled");
		cacheExpirySeconds = configuration.getInt(BANS_CACHE_DURATION_SECONDS_KEY);
	}

	@Override
	public boolean shouldEnable() {
		return shouldEnable;
	}

	@Override
	public BanComponent createComponent() {
		if (shouldEnable) {
			BanComponentConfiguration banComponentConfiguration = new BanComponentConfiguration(shouldCache, Duration.of(cacheExpirySeconds, ChronoUnit.SECONDS), "");
			return new BanComponent(banComponentConfiguration, plugin, uuidService);
		}
		return null;
	}
}

