package net.opticraft.bungee.plugin.bans;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.opticraft.bungee.plugin.uuid.UuidService;
import net.opticraft.bungee.plugin.bans.commands.BanCommand;
import net.opticraft.bungee.plugin.bans.service.*;
import net.opticraft.bungee.plugin.core.OpticraftBungeeComponent;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

public class BanComponent implements OpticraftBungeeComponent {
	private final BanComponentConfiguration banComponentConfiguration;
	private final BanService<UUID> userBanService;
	private final BanService<String> ipBanService;
	private final Plugin plugin;
	private final UuidService uuidService;

	public BanComponent(BanComponentConfiguration banComponentConfiguration, Plugin plugin, UuidService uuidService) {
		this.uuidService = uuidService;
		ExecutorService executorService = plugin.getProxy().getScheduler().unsafe().getExecutorService(plugin);

		BanService<UUID> userBanService = new SimpleUserBanService(executorService);
		if (banComponentConfiguration.shouldCache) {
			userBanService = new CachingBanService<>(userBanService, banComponentConfiguration.cacheDuration);
		}

		BanService<String> ipBanService = new SimpleIpBanService(executorService);
		if (banComponentConfiguration.shouldCache) {
			userBanService = new CachingBanService<>(userBanService, banComponentConfiguration.cacheDuration);
		}

		this.plugin = plugin;
		this.userBanService = userBanService;
		this.ipBanService = ipBanService;
		this.banComponentConfiguration = banComponentConfiguration;
	}

	@Override
	public Command[] getCommands() {
		return new Command[] {
				new BanCommand(this.userBanService, uuidService)
		};
	}

	@Override
	public void stop() {

	}

	// TODO: Should we do event handling within components?
	@EventHandler
	public void onPlayerLogin(LoginEvent loginEvent) {
		// TODO: This is going to fail when we have multiple event listeners for the same event.
		// - will have to build our own intent based abstraction later
		loginEvent.registerIntent(plugin);

		// Concurrently lookup username ban and ip ban, then once both come back we decide whether to let the user in.
		// This could be optimized to reject banned users quicker, but given its the edge case, its not worth the added complexity
		CompletionStage<Instant> userBanDateFuture = userBanService.getUnbanDate(loginEvent.getConnection().getUniqueId());
		CompletionStage<Instant> ipBanDateFuture = ipBanService.getUnbanDate(loginEvent.getConnection().getAddress().getAddress().getHostAddress());
		userBanDateFuture.thenCombine(ipBanDateFuture, (userBanDate, ipBanDate) -> {
			if (isBanned(userBanDate) || isBanned(ipBanDate)) {
				loginEvent.setCancelled(true);
				loginEvent.setCancelReason(banComponentConfiguration.banMessage);
			}
			return null;
		}).whenComplete((result, error) -> {
			loginEvent.completeIntent(plugin);
		});
	}

	private boolean isBanned(Instant userBanDate) {
		return userBanDate != null && userBanDate.isAfter(Instant.now());
	}
}