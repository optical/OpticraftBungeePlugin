package net.opticraft.bungee.plugin.bans;

import net.opticraft.bungee.plugin.util.concurrency.ResultTask;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Future;

// Interface for dealing with bans off a certain key.
// In practice, keys are either IPs, or uuids of players
public interface BanService<TBanKey> {
	ResultTask<BanLookupResult<TBanKey>> getFullBanDetails(TBanKey id);
	ResultTask<Instant> getUnbanDate(TBanKey id);
	ResultTask<Boolean> unban(TBanKey id, UUID unbannedId, String unbanReason);
	ResultTask<Boolean> ban(TBanKey id, UUID bannerId, String banReason, Instant expiryDate);
	ResultTask<ArrayList<SimpleBanDetails>> getAllActiveBans();
}
