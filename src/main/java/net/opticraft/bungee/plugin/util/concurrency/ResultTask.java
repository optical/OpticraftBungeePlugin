package net.opticraft.bungee.plugin.util.concurrency;

import net.opticraft.bungee.plugin.util.ParamRunnable;

// A very rough promise/Task implementation
public interface ResultTask<T> {
	void then(ParamRunnable<TaskResultWrapper<T>> callback);
	// TODO: Implement chaining of thens
}
