package net.opticraft.bungee.plugin.bans.service;

import java.util.ArrayList;

public class IpBanLookupResult {
	public final String ipAddress;
	public final ArrayList<BanDetails> banDetails;

	public IpBanLookupResult(String ipAddress, ArrayList<BanDetails> banDetails) {
		this.ipAddress = ipAddress;
		this.banDetails = banDetails;
	}
}
