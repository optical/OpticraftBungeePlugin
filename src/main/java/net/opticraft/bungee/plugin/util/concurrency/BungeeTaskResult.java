package net.opticraft.bungee.plugin.util.concurrency;

public class BungeeTaskResult<T> implements TaskResultWrapper<T> {
	private final T result;
	private final Throwable error;

	public BungeeTaskResult(T result) {
		this.result = result;
		this.error = null;
	}

	public BungeeTaskResult(Throwable throwable) {
		this.result = null;
		this.error = throwable;
	}

	@Override
	public T get() {
		if (error != null) {
			throw new RuntimeException("Original task faulted. Check the inner exception for the cause", error);
		}
		return result;
	}
}
