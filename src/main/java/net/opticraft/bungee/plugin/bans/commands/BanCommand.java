package net.opticraft.bungee.plugin.bans.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.opticraft.bungee.plugin.UUIDService;
import net.opticraft.bungee.plugin.bans.service.BanService;
import net.opticraft.bungee.plugin.core.commands.OpticraftCommand;
import net.opticraft.bungee.plugin.util.TimeFormatter;

import java.time.Instant;
import java.util.UUID;


public class BanCommand extends OpticraftCommand {
	private final BanService<UUID> userBanService;
	private final UUIDService uuidService;

	public BanCommand(BanService<UUID> userBanService, UUIDService uuidService) {
		super("ban", "wat");
		this.userBanService = userBanService;
		this.uuidService = uuidService;
	}

	@Override
	protected int getMinArgs() {
		return 1;
	}

	@Override
	protected BaseComponent[] getHelpMessage() {
		return new ComponentBuilder("Incorrect usage! Synax: /ban <name> [reason] [time]")
				.color(ChatColor.RED)
				.create();
	}

	@Override
	protected void run(CommandSender commandSender, String[] args) {
		String userToBan = args[0];
		long timeToBan = 0;
		StringBuilder reasonBuilder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("t:")) {
				timeToBan = TimeFormatter.parseTime(args[i].replaceAll("t:", ""));
				break;
			} else {
				reasonBuilder.append(args[i]).append(' ');
			}
		}

		String reason = reasonBuilder.length() > 0 ? reasonBuilder.toString().trim() : "No reason provided.";
		Instant expiryTime = timeToBan > 0 ? Instant.now().plusMillis(timeToBan) : null;

		uuidService.getUniqueId(userToBan).thenApply(userId -> {
			if (userId != null) {
				return userBanService.ban(null, null, reason, expiryTime).thenAccept(success -> {
					if (success) {
						commandSender.sendMessage(new ComponentBuilder(String.format("Successfully banned \"%s\"")).color(ChatColor.RED).create());
					} else {
						commandSender.sendMessage(new ComponentBuilder(String.format("User \"%s\" is already banned.")).color(ChatColor.RED).create());
					}
				});
			} else {
				commandSender.sendMessage(new ComponentBuilder(String.format("There is no minecraft user by the name of \"%s\"", userToBan)).color(ChatColor.RED).create());
				return null;
			}
		}).whenComplete((result, error) -> {
			if (error != null) {
				commandSender.sendMessage(new ComponentBuilder("Failed to ban user due to an internal, please try again").color(ChatColor.RED).create());
				// TODO: Need to log an error here.
				// Would probably pay to extract this out into a helper somewhere, since its going to be a common situation
			}
		});
	}
}
