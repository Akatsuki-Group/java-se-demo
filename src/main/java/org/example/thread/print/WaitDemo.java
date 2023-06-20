package org.example.thread.print;

/**
 * 两个线程交替打印
 *
 * @author arthur
 */
public class WaitDemo {
    static int i = 0;
    static final Object lock = new Object();


    public static void main(String[] args) {
        new Thread(new Task(),"奇数线程").start();
        new Thread(new Task(),"偶数线程").start();
    }


    static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread() + ":" + ++i);
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (i>100){
                    break;
                }
            }
        }
    }
}
