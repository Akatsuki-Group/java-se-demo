package org.example.fibers;

import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Standard {

    public static void main(String[] args) throws Exception{
        CountDownLatch count  = new CountDownLatch(10000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        IntStream.range(0,10000).forEach(i-> executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) { }
            count.countDown();
        }));
        count.await();
        stopWatch.stop();
        System.out.println("结束了: " + stopWatch.prettyPrint());
        executorService.shutdownNow();
    }
}