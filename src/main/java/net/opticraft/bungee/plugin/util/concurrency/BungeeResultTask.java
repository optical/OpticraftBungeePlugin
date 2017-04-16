package net.opticraft.bungee.plugin.util.concurrency;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.opticraft.bungee.plugin.util.ParamRunnable;

import java.util.ArrayList;
import java.util.function.Supplier;

public class BungeeResultTask<T> implements ResultTask<T> {
	private final Plugin plugin;
	private final TaskScheduler scheduler;
	private final Supplier<T> workItem;
	private final Object callbackLock = new Object();
	// We don't use a concurrent list here on purpose, since we need to provide atomicity around checking for a result & subscription in addition to the usual threadsafe list operations
	private ArrayList<ParamRunnable<TaskResultWrapper<T>>> callbacks;

	private TaskResultWrapper<T> taskResult;
	private boolean hasRun;

	public BungeeResultTask(Plugin plugin, TaskScheduler scheduler, Supplier<T> workItem) {
		this.plugin = plugin;
		this.scheduler = scheduler;
		this.workItem = workItem;
		callbacks = new ArrayList<>();
	}

	public synchronized void run() {
		if (hasRun) {
			throw new RuntimeException("BungeeResultTask has already been scheduled!");
		}
		hasRun = true;
		scheduler.runAsync(plugin, this::execute);
	}

	private void execute() {
		try {
			T result = workItem.get();
			taskResult = new BungeeTaskResult<>(result);
		} catch (Throwable error) {
			taskResult = new BungeeTaskResult<>(error);
		}

		ArrayList<ParamRunnable<TaskResultWrapper<T>>> localCallbacks;
		// lock and copy the list. Should ensure we cannot miss a callback, even if they subscribed after completion, but before invocation
		synchronized (callbackLock) {
			localCallbacks = new ArrayList<>(callbacks);
			callbacks = null; // release the callbacks list
		}

		for (ParamRunnable<TaskResultWrapper<T>> callback : localCallbacks) {
			try {
				callback.run(taskResult);
			} catch (Throwable exception) {
				// This is quite bad, we have no real recourse for coping with this
			}
		}
	}

	@Override
	public void then(ParamRunnable<TaskResultWrapper<T>> callback) {
		synchronized (callbackLock) {
			if (taskResult == null) {
				callbacks.add(callback);
			}
		}

		if (taskResult != null) {
			callback.run(taskResult);
		}
	}
}
