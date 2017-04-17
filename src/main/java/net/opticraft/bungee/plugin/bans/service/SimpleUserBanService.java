package net.opticraft.bungee.plugin.bans.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class SimpleUserBanService implements BanService<UUID> {
	private final Executor executor;

	public SimpleUserBanService(Executor executor) {
		this.executor = executor;
	}

	@Override
	public CompletionStage<Instant> getUnbanDate(UUID userId) {
		// todo: perform the unban
		return null;
	}

	public CompletionStage<BanLookupResult<UUID>> getFullBanDetails(UUID userId) {
		return null;
	}

	public CompletionStage<Boolean> unban(UUID userId, UUID unbannedId, String unbanReason) {
		// todo: perform the unban
		return null;
	}

	@Override
	public CompletionStage<Boolean> ban(UUID userId, UUID bannerId, String banReason, Instant expiryDate) {
		// todo: perform the unban
		return null;
	}

	@Override
	public CompletionStage<ArrayList<SimpleBanDetails>> getAllActiveBans() {
		// todo: perform the unban
		return null;
	}
}
