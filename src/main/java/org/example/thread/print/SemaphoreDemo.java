package org.example.thread.print;

import java.util.concurrent.Semaphore;

/**
 * 两个线程交替打印
 */
public class SemaphoreDemo {
    static Semaphore s1 = new Semaphore(1);
    static Semaphore s2 = new Semaphore(0);
    static int i = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    s1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i > 100) break;
                System.out.println(Thread.currentThread() + ":" + i++);
                s2.release();
            }
        }, "偶数线程").start();

        new Thread(() -> {
            while (true) {
                try {
                    s2.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i > 100) break;
                System.out.println(Thread.currentThread() + ":" + i++);
                s1.release();
            }
        }, "奇数线程").start();
    }
}