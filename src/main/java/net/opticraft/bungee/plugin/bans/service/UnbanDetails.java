package net.opticraft.bungee.plugin.bans.service;

import java.time.Instant;
import java.util.UUID;

public class UnbanDetails {
	public final UUID unbanUserId;
	public final String unbanReason;
	public final Instant unbanDate;

	public UnbanDetails(UUID unbanUserId, String unbanReason, Instant unbanDate) {
		this.unbanUserId = unbanUserId;
		this.unbanReason = unbanReason;
		this.unbanDate = unbanDate;
	}
}
