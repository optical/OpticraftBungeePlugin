package net.opticraft.bungee.plugin.bans.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

// Interface for dealing with bans off a certain key.
// In practice, keys are either IPs, or uuids of players
public interface BanService<TBanKey> {
	CompletionStage<BanLookupResult<TBanKey>> getFullBanDetails(TBanKey id);
	CompletionStage<Instant> getUnbanDate(TBanKey id);
	CompletionStage<Boolean> unban(TBanKey id, UUID unbannedId, String unbanReason);
	CompletionStage<Boolean> ban(TBanKey id, UUID bannerId, String banReason, Instant expiryDate);
	CompletionStage<ArrayList<SimpleBanDetails>> getAllActiveBans();
}
