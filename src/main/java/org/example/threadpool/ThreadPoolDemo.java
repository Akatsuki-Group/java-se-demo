package org.example.threadpool;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService1 = Executors.newCachedThreadPool();//快
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);//慢
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();//最慢


        RejectedExecutionExecuteDemo rejectedExecutionExecuteDemo = new RejectedExecutionExecuteDemo();

        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @SneakyThrows
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // 写数据库的代码
                System.out.println("线程池满了，我要写数据库了");
                rejectedExecutionExecuteDemo.getQueue().put(r);
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10), rejectedExecutionHandler);//自定义线程


        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(threadPoolExecutor);
        List<ListenableFuture<String>> futures = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            //threadPoolExecutor.execute(new MyTask(i));
            futures.add(listeningExecutorService.submit(new MyCallable()));
        }
        rejectedExecutionExecuteDemo.invoke();
        List<String> results = Futures.allAsList(futures).get();
        for (String result : results) {
            System.out.println(result);
        }
    }
}

class RejectedExecutionExecuteDemo {

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private boolean isRunning = true;

    public void setQueue(LinkedBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public LinkedBlockingQueue<Runnable> getQueue() {
        return queue;
    }

    public void invoke() {
        while (!queue.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Runnable take = null;
                    try {
                        take = queue.take();
                        System.out.println("任务执行成功");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("线程池满了，我要重新处理了" + take);
                    take.run();
                }
            }).start();
        }
    }
}


/***
 * 项目
 */
class MyTask implements Runnable {
    int i = 0;

    public MyTask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "程序员做第" + i + "个项目");
        try {
            Thread.sleep(3000L);//业务逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(300L);//业务逻辑
        return "monkey";
    }
}
