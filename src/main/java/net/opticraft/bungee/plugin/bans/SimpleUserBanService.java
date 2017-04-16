package net.opticraft.bungee.plugin.bans;


import net.opticraft.bungee.plugin.util.concurrency.BungeeTaskFactory;
import net.opticraft.bungee.plugin.util.concurrency.ResultTask;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class SimpleUserBanService implements BanService<UUID> {
	private final BungeeTaskFactory taskFactory;

	public SimpleUserBanService(BungeeTaskFactory taskFactory) {
		this.taskFactory = taskFactory;
	}


	public ResultTask<BanLookupResult<UUID>> getFullBanDetails(UUID userId) {
		// TODO: Implement this thing
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<Instant> getUnbanDate(UUID userId) {
		// todo: perform the unban
		return taskFactory.run(() -> null);
	}

	public ResultTask<Boolean> unban(UUID userId, UUID unbannedId, String unbanReason) {
		// todo: perform the unban
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<Boolean> ban(UUID userId, UUID bannerId, String banReason, Instant expiryDate) {
		// todo: perform the unban
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<ArrayList<SimpleBanDetails>> getAllActiveBans() {
		// todo: perform the unban
		return taskFactory.run(() -> null);
	}
}
