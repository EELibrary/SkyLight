package org.wangxyper.JavaUtils;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ConcurrentExecutor<E> implements Executor{
    private final int prp = 8;
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    //Thread factories
    private final ThreadFactory factory_Concurrent = r -> {
        Thread thread = new Thread(r,"Concurrent-Task-Executor");
        thread.setDaemon(true);
        thread.setPriority(prp);
        return thread;
    };
    private final ThreadFactory factory_Other = r -> {
        Thread thread = new Thread(r,"Async-Task-Executor");
        thread.setDaemon(true);
        thread.setPriority(prp);
        return thread;
    };
    private final ThreadPoolExecutor executor_Concurrent = new ThreadPoolExecutor(2,Integer.MAX_VALUE,Long.MAX_VALUE, TimeUnit.DAYS,new LinkedBlockingQueue<>(),factory_Concurrent);
    private final ThreadPoolExecutor executor_Other = new ThreadPoolExecutor(2,Integer.MAX_VALUE,Long.MAX_VALUE,TimeUnit.DAYS,new LinkedBlockingQueue<>(),factory_Other);
    private final Logger LOGGER = Logger.getAnonymousLogger();
    private final int threadCount;

    public void postConcurrentTask(Runnable task){
        tasks.add(task);
    }
    public ConcurrentExecutor(int threadCount){
        executor_Concurrent.setCorePoolSize(threadCount);
        executor_Other.setCorePoolSize(threadCount);
        this.threadCount = threadCount;
        this.init();
    }
    public ConcurrentExecutor(){
        this(Runtime.getRuntime().availableProcessors());
    }
    private void init(){
        //The body
        final Runnable concurrentTask = () -> {
            while(true){
                if(tasks.size()!=0){
                    for (Runnable task:tasks) {
                        try {
                            task.run();
                        }catch (Exception e){
                            LOGGER.warning("Failed to execute task!");
                            e.printStackTrace();
                        }finally {
                            tasks.remove(task);
                        }
                    }
                }else{
                    try {
                        Thread.sleep(0,1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //Create new threads to execute the concurrent task
        for (int i = 0; i < this.threadCount; i++) {
            executor_Concurrent.execute(concurrentTask);
        }
    }
    //Remove the task from the task list
    public void removeTask(Runnable target){
        tasks.remove(target);
    }
    //No spacial
    @Override
    public void execute(Runnable target){
        executor_Other.execute(target);
    }
    //Wait the tasks
    public void waitConcurrentTasks(long timeOut){
        //Cast to nano seconds
        long time = timeOut*1000;
        while(tasks.size()!=0&&time>0){
            try {
                time--;
                Thread.sleep(0,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(tasks.size()!=0){
            LOGGER.warning("Time was outed!");
        }
    }
    private final AtomicInteger active = new AtomicInteger(0);
}
