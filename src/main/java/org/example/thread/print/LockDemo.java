package org.example.thread.print;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author arthur
 */
public class LockDemo {
    static int i = 0;

    static Lock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    if (i > 100) break;

                    System.out.println(Thread.currentThread() + ":" + i++);
                    condition.signal();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }, "偶数线程").start();

        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    if (i > 100) break;
                    System.out.println(Thread.currentThread() + ":" + i++);
                    condition.signal();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }, "奇数线程").start();
    }


}
