package net.opticraft.bungee.plugin.uuid;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class HttpUuidService implements UuidService {
	private final Executor executor;

	public HttpUuidService(Executor executor) {
		this.executor = executor;
	}

	@Override
	public CompletionStage<UUID> getUniqueId(String username) {
		return CompletableFuture.supplyAsync(() -> syncGetUniqueId(username), executor);
	}

	private UUID syncGetUniqueId(String username) {
		try {
			InputStream inputStream = new URL("https://api.mojang.com/users/profiles/minecraft/" + username).openStream();
			MojangUsernameResponse response = new Gson().fromJson(new InputStreamReader(inputStream), MojangUsernameResponse.class);
			return buildUuid(response.id);
		} catch (IOException exception) {
			// Because checked exceptions are difficult
			throw new RuntimeException(exception);
		}
	}

	private static UUID buildUuid(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
	}
}
