package net.opticraft.bungee.plugin.core;

import net.md_5.bungee.api.plugin.Listener;

public interface OpticraftBungeeComponent extends Listener {
	void initialize(); // Maybe for config setup?

	void readConfig(); // Load config

	boolean shouldEnable();

	void bindSubscriptions();
}
