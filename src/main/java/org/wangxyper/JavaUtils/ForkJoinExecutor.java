package org.wangxyper.JavaUtils;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class ForkJoinExecutor<E> {
    private final ForkJoinPool pool;
    public ForkJoinExecutor(){
        this(Runtime.getRuntime().availableProcessors());
    }
    public ForkJoinExecutor(int threads){
        this.pool = new ForkJoinPool(threads);
    }
    public void runThenWait(Iterable<E> list, Consumer<E> consumer,long timeOut) {
        List<E> cast = Lists.newArrayList(list);
        ForkJoinTask<?> task = pool.submit(new ForkTask<>(cast, consumer));
        try {
            task.get(timeOut, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            if(!(e instanceof TimeoutException||e instanceof InterruptedException)){
                e.printStackTrace();
            }
        }
    }
    public void runThenWait(Iterable<E> list, Consumer<E> consumer) {
        List<E> cast = Lists.newArrayList(list);
        ForkJoinTask<?> task = pool.submit(new ForkTask<>(cast, consumer));
        task.join();
    }
    public void run(Iterable<E> list, Consumer<E> consumer) {
        List<E> cast = Lists.newArrayList(list);
        pool.execute(new ForkTask<>(cast, consumer));
    }
    public static final class ForkTask<T> extends RecursiveAction {
        private static final int THRESHOLD = 50; //每个子线程执行的任务数量
        private final int start;
        private final int end;
        private final List<T> list;
        private final Consumer<T> task;

        public ForkTask(List<T> list, Consumer<T> consumer) {
            this.list = list;
            this.task = consumer;
            this.start = 0;
            this.end = list.size();
        }

        private ForkTask(int start, int end, List<T> list, Consumer<T> consumer) {
            this.list = list;
            this.task = consumer;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                for (int i = start; i < end; i++) {
                    task.accept(list.get(i));
                }
            } else {
                int middle = (start + end) / 2;
                new ForkTask<>(start, middle, this.list, this.task).fork();
                new ForkTask<>(middle, end, this.list, this.task).fork();
            }

        }
    }
}
