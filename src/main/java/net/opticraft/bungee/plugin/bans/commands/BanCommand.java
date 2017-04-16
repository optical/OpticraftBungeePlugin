package net.opticraft.bungee.plugin.bans.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.opticraft.bungee.plugin.bans.BanService;
import net.opticraft.bungee.plugin.core.commands.OpticraftCommand;
import net.opticraft.bungee.plugin.util.TimeFormatter;

import java.time.Instant;
import java.util.UUID;


public class BanCommand extends OpticraftCommand {
	private final BanService<UUID> userBanService;

	public BanCommand(BanService<UUID> userBanService) {
		super("ban", "wat");
		this.userBanService = userBanService;
	}

	@Override
	protected int getMinArgs() {
		return 1;
	}

	@Override
	protected BaseComponent getHelpMessage() {
		BaseComponent component = new TextComponent("Incorrect usage! Synax: /ban <name> [reason] [time]");
		component.setColor(ChatColor.RED);
		return component;
	}

	@Override
	protected void run(CommandSender commandSender, String[] args) {
		String userToBan = args[0];
		long timeToBan = 0;
		// TODO: Need some UUID management nonsense.
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

		// TODO: Callback?
		userBanService.ban(null, null, reason, expiryTime).then(resultWrapper -> {
			try {
				boolean success = resultWrapper.get();
			} catch (Throwable error) {
				// TODO: think about how to log errors
				// TODO: Can I interact with the command sender here?
			}
		});
	}
}
