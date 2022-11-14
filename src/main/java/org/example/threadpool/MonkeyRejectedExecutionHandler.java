package org.example.threadpool;

import java.util.concurrent.*;

public class MonkeyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("线程池满了，我要写数据库了");
        System.out.println(executor);
    }
}
