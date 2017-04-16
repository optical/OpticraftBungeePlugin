package net.opticraft.bungee.plugin.bans;

import net.opticraft.bungee.plugin.util.concurrency.BungeeTaskFactory;
import net.opticraft.bungee.plugin.util.concurrency.ResultTask;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Future;

public class SimpleIpBanService implements BanService<String> {
	private final BungeeTaskFactory taskFactory;

	public SimpleIpBanService(BungeeTaskFactory taskFactory) {
		this.taskFactory = taskFactory;
	}

	@Override
	public ResultTask<BanLookupResult<String>> getFullBanDetails(String id) {
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<Instant> getUnbanDate(String id) {
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<Boolean> unban(String id, UUID unbannedId, String unbanReason) {
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<Boolean> ban(String id, UUID bannerId, String banReason, Instant expiryDate) {
		return taskFactory.run(() -> null);
	}

	@Override
	public ResultTask<ArrayList<SimpleBanDetails>> getAllActiveBans() {
		return taskFactory.run(() -> null);
	}
}
