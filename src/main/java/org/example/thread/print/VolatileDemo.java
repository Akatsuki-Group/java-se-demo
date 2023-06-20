package org.example.thread.print;

/**
 * 两个线程交替打印
 * @author arthur
 */
public class VolatileDemo {
    static int i=0;

    static volatile boolean flag = true;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (flag){
                        System.out.println(Thread.currentThread()+":"+i++);
                        flag = false;
                    }
                    if (i>100) break;
                }
            }
        },"偶数线程").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (!flag){
                        System.out.println(Thread.currentThread()+":"+i++);
                        flag = true;
                    }
                    if (i>100) break;
                }
            }
        },"奇数线程").start();
    }
}