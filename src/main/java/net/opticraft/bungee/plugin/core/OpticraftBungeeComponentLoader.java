package net.opticraft.bungee.plugin.core;

import net.md_5.bungee.config.Configuration;

public interface OpticraftBungeeComponentLoader<T extends OpticraftBungeeComponent> { // TODO: T should extend the component
	void initializeConfig(Configuration configuration);

	void readConfig(Configuration configuration); // Load config

	boolean shouldEnable();

	T createComponent();
}
