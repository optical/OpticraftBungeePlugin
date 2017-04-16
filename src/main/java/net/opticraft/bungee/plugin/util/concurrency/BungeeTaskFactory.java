package net.opticraft.bungee.plugin.util.concurrency;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.function.Supplier;

public class BungeeTaskFactory {
	private final Plugin plugin;
	private final TaskScheduler taskScheduler;

	public BungeeTaskFactory(Plugin plugin, TaskScheduler taskScheduler) {
		this.plugin = plugin;
		this.taskScheduler = taskScheduler;
	}

	public <T> ResultTask<T> run(Supplier<T> workItem) {
		BungeeResultTask<T> task = new BungeeResultTask<>(plugin, taskScheduler, workItem);
		task.run();
		return task;
	}
}
