package net.opticraft.bungee.plugin.util;

import net.md_5.bungee.config.Configuration;

public class ConfigHelper {
	public static void addConfigDefault(Configuration configuration, String key, int value) {
		unsafeAddConfigDefault(configuration, key, value);
	}

	public static void addConfigDefault(Configuration configuration, String key, float value) {
		unsafeAddConfigDefault(configuration, key, value);
	}

	public static void addConfigDefault(Configuration configuration, String key, double value) {
		unsafeAddConfigDefault(configuration, key, value);
	}

	public static void addConfigDefault(Configuration configuration, String key, boolean value) {
		unsafeAddConfigDefault(configuration, key, value);
	}

	public static void addConfigDefault(Configuration configuration, String key, String value) {
		unsafeAddConfigDefault(configuration, key, value);
	}

	private static void unsafeAddConfigDefault(Configuration configuration, String key, Object value) {
		if (configuration.get(key, null) == null) {
			configuration.set(key, value);
		}
	}
}
