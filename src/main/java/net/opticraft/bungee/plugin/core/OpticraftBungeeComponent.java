package net.opticraft.bungee.plugin.core;

import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;

public interface OpticraftBungeeComponent extends Listener {
	Command[] getCommands();
	void stop();
}
