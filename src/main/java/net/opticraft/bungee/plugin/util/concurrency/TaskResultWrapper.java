package net.opticraft.bungee.plugin.util.concurrency;

public interface TaskResultWrapper<T> {
	T get(); //gets the result of the task. If the task faulted, this will raise an exception
}
