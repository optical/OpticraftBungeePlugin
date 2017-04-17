package net.opticraft.bungee.plugin.bans.service;

import java.util.ArrayList;

public class BanLookupResult<TBanKey> {
	public final TBanKey id;
	public final ArrayList<BanDetails> banDetails;

	public BanLookupResult(TBanKey id, ArrayList<BanDetails> banDetails) {
		this.id = id;
		this.banDetails = banDetails;
	}

	public boolean isBanned() {
		return banDetails.stream().anyMatch(details -> details.isActive());
	}
}
