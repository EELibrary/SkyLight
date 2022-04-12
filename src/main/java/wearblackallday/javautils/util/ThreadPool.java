package wearblackallday.javautils.util;


import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ThreadPool {
	private ThreadPoolExecutor executor;
	private final int threadCount;
	private final AtomicInteger  activeCount = new AtomicInteger(0);

	public ThreadPool() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public ThreadPool(int threadCount) {
		this.threadCount = threadCount;
		this.restart();
	}

	public int getThreadCount() {
		return this.threadCount;
	}

	public int getActiveCount() {
		return this.activeCount.get();
	}

	public ThreadPoolExecutor getExecutor() {
		return this.executor;
	}

	public void execute(Runnable action) {
		this.activeCount.incrementAndGet();

		this.executor.execute(() -> {
			try {
				action.run();
			}catch(Exception e){
				e.printStackTrace();
			} finally {
			  this.activeCount.decrementAndGet();
			}
		});
	}

	public <T> void execute(Iterator<T> iterator, Consumer<T> action) {
		iterator.forEachRemaining(t -> this.execute(() -> action.accept(t)));
	}

	public <T> void execute(Iterable<T> iterable, Consumer<T> action) {
		iterable.forEach(t -> this.execute(() -> action.accept(t)));
	}

	public <T> void execute(Stream<T> stream, Consumer<T> action) {
		stream.forEach(t -> this.execute(() -> action.accept(t)));
	}

	public void execute(IntStream stream, IntConsumer action) {
		stream.forEach(t -> this.execute(() -> action.accept(t)));
	}

	public void execute(LongStream stream, LongConsumer action) {
		stream.forEach(t -> this.execute(() -> action.accept(t)));
	}

	public void execute(DoubleStream stream, DoubleConsumer action) {
		stream.forEach(t -> this.execute(() -> action.accept(t)));
	}

	public <T> void execute(T[] array, Consumer<T> action) {
		for(T t : array) this.execute(() -> action.accept(t));
	}

	public void execute(boolean[] array, Consumer<Boolean> action) {
		for(boolean t : array) this.execute(() -> action.accept(t));
	}

	public void execute(byte[] array, Consumer<Byte> action) {
		for(byte t : array) this.execute(() -> action.accept(t));
	}

	public void execute(short[] array, Consumer<Short> action) {
		for(short t : array) this.execute(() -> action.accept(t));
	}

	public void execute(int[] array, IntConsumer action) {
		for(int t : array) this.execute(() -> action.accept(t));
	}

	public void execute(float[] array, Consumer<Float> action) {
		for(float t : array) this.execute(() -> action.accept(t));
	}

	public void execute(long[] array, LongConsumer action) {
		for(long t : array) this.execute(() -> action.accept(t));
	}

	public void execute(double[] array, DoubleConsumer action) {
		for(double t : array) this.execute(() -> action.accept(t));
	}

	public void execute(char[] array, Consumer<Character> action) {
		for(char t : array) this.execute(() -> action.accept(t));
	}

	public void awaitCompletion() {
		while(this.activeCount.get()!=0){
			try {
				Thread.sleep(0,1);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void restart() {
		if(this.executor == null || this.executor.isShutdown()) {
			this.executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(this.threadCount);
		}
	}

	public void shutdown() {
		this.executor.shutdown();
	}

	public boolean isShutdown() {
		return this.executor.isShutdown();
	}

}