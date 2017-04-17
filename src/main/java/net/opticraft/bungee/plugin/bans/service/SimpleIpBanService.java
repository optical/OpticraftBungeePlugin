package net.opticraft.bungee.plugin.bans.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class SimpleIpBanService implements BanService<String> {
	private final Executor executor;

	public SimpleIpBanService(Executor executor) {
		this.executor = executor;
	}

	@Override
	public CompletionStage<BanLookupResult<String>> getFullBanDetails(String id) {
		return null;
	}

	@Override
	public CompletionStage<Instant> getUnbanDate(String id) {
		return null;
	}

	@Override
	public CompletionStage<Boolean> unban(String id, UUID unbannedId, String unbanReason) {
		return null;
	}

	@Override
	public CompletionStage<Boolean> ban(String id, UUID bannerId, String banReason, Instant expiryDate) {
		return null;
	}

	@Override
	public CompletionStage<ArrayList<SimpleBanDetails>> getAllActiveBans() {
		return null;
	}
}
