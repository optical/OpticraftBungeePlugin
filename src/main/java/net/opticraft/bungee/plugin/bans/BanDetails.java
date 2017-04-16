package net.opticraft.bungee.plugin.bans;

import java.time.Instant;
import java.util.UUID;

public class BanDetails {
	public final UUID bannedById;
	public final String banReason;
	public final Instant expiryDate;
	public final UnbanDetails unbanDetails;

	public BanDetails(UUID bannedById, String banReason, Instant expiryDate, UnbanDetails unbanDetails) {
		this.bannedById = bannedById;
		this.banReason = banReason;
		this.expiryDate = expiryDate;
		this.unbanDetails = unbanDetails;
	}

	public boolean isActive() {
		return this.unbanDetails != null && (this.expiryDate == null || this.expiryDate.isAfter(Instant.now()));
	}
}
