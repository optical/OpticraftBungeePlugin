package net.opticraft.bungee.plugin.bans.service;

import java.time.Instant;

public class SimpleBanDetails<TBanKey> {
	public final TBanKey id;
	public final Instant expiryDate;

	public SimpleBanDetails(TBanKey id, Instant expiryDate) {
		this.id = id;
		this.expiryDate = expiryDate;
	}
}
