package net.opticraft.bungee.plugin.uuid;

import net.md_5.bungee.config.Configuration;
import net.opticraft.bungee.plugin.util.ConfigHelper;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executor;

public class UuidServiceFactory {
	public static final String UUID_CACHING_DURATION_SECONDS_KEY = "uuid.caching.durationSeconds";
	public static final String UUID_CACHING_ENABLED_KEY = "uuid.caching.enabled";

	private boolean shouldCache;
	private Duration cacheDuration;

	public void initializeConfig(Configuration configuration) {
		ConfigHelper.addConfigDefault(configuration, UUID_CACHING_ENABLED_KEY, true);
		ConfigHelper.addConfigDefault(configuration, UUID_CACHING_DURATION_SECONDS_KEY, true);

		shouldCache = configuration.getBoolean(UUID_CACHING_ENABLED_KEY);
		cacheDuration = Duration.of(configuration.getInt(UUID_CACHING_DURATION_SECONDS_KEY), ChronoUnit.SECONDS);
	}

	public UuidService create(Executor executor) {
		UuidService uuidService = new HttpUuidService(executor);
		if (shouldCache) {
			return new CachingUuidService(uuidService, cacheDuration);
		}
		return uuidService;
	}
}
