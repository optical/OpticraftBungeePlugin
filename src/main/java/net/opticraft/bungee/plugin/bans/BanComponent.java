package net.opticraft.bungee.plugin.bans;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.event.EventHandler;
import net.opticraft.bungee.plugin.core.OpticraftBungeeComponent;

public class BanComponent implements OpticraftBungeeComponent {

	@Override
	public void initialize() {

	}

	@Override
	public void readConfig() {

	}

	@Override
	public boolean shouldEnable() {
		return false;
	}

	@Override
	public void bindSubscriptions() {

	}

	@EventHandler
	public void onPlayerLogin(LoginEvent loginEvent) {
	}
}