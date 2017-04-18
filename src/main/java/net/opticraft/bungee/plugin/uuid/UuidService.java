package net.opticraft.bungee.plugin.uuid;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

// Responsible for resolving uuid -> username lookups
public interface UuidService {
	CompletionStage<UUID> getUniqueId(String username);
}
