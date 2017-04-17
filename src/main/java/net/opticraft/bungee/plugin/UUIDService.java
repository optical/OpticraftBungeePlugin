package net.opticraft.bungee.plugin;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

// Responsible for resolving uuid -> username lookups
public interface UUIDService {
	CompletionStage<UUID> getUniqueId(String username);
}
