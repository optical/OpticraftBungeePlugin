package net.opticraft.bungee.plugin.core.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;

public abstract class OpticraftCommand extends Command {
	protected OpticraftCommand(String commandName) {
		super(commandName);
	}

	protected OpticraftCommand(String commandName, String permission) {
		super(commandName, permission);
	}

	protected abstract int getMinArgs();

	protected abstract BaseComponent getHelpMessage();

	protected abstract void run(CommandSender commandSender, String[] args);

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (getMinArgs() < args.length) {
			sender.sendMessage(getHelpMessage());
			return;
		}
		run(sender, args);
	}
}
