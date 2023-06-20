package org.example.thread.print;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 两个线程交替打印
 */
public class CyclicBarrierDemo {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    static int i = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                if (i > 100) break;
                if (i % 2 == 0) {
                    System.out.println(Thread.currentThread() + ":" + i++);
                }
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                  e.printStackTrace();
                }
            }

        }, "偶数线程").start();

        new Thread(() -> {
            while (true) {
                if (i > 100) break;
                if (i % 2 == 1) {
                    System.out.println(Thread.currentThread() + ":" + i++);
                }
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "奇数线程").start();
    }
}

