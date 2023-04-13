package org.example.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现三个线程交替打印
 *
 * @author freedom wang
 * @date 2021-02-22 21:42:44
 */
public class ThreeThreadsAlternatePrint {

    private static final ReentrantLock lock = new ReentrantLock();
    private static Condition c1 = lock.newCondition();
    private static Condition c2 = lock.newCondition();
    private static Condition c3 = lock.newCondition();

    /**
     * 解题思路：使用显示锁
     *
     * @author freedom wang
     * @date 2021-02-22 21:47:36
     */
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                lock.lock();

                for (int i = 0; i < 10; i++) {
                    System.out.println("A - " + i);
                    c2.signal();
                    c1.await();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();

                for (int i = 0; i < 10; i++) {
                    System.out.println("B - " + i);
                    c3.signal();
                    c2.await();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();

                for (int i = 0; i < 10; i++) {
                    System.out.println("C - " + i);
                    c1.signal();
                    c3.await();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

    }
}

