package org.example.juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arthur
 */
public class CountDownLatchTest4 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        executorService.submit(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程A执行，执行时间：" + System.currentTimeMillis());
        });

        executorService.submit(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程B执行，执行时间：" + System.currentTimeMillis());
        });

        executorService.submit(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程C执行，执行时间：" + System.currentTimeMillis());
        });

        countDownLatch.countDown();

    }
}
