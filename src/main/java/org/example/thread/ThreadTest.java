package org.example.thread;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ThreadTest {
    public static void main(String[] args) throws Exception {
        new Thread(new MyRunnable()).start();
        Callable<String> monkey = Executors.callable(new MyRunnable(), "monkey");
        System.out.println(monkey.call());
    }


    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("MyRunnable");
        }
    }

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("MyCallable");
            return "success";
        }
    }
}
