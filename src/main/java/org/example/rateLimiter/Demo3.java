package org.example.rateLimiter;

import cn.hutool.core.util.RandomUtil;
import com.google.common.util.concurrent.RateLimiter;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 微信公众号：程序员路人，个人博客：http://www.itsoku.com
 */
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test(){
        RateLimiter rateLimiter = RateLimiter.create(5);//设置QPS为5
        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }
        System.out.println("----------");
        //可以随时调整速率，我们将qps调整为10
        rateLimiter.setRate(10);
        for (int i = 0; i < 10; i++) {
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void test1() {
        //0.5代表一秒最多多少个
        RateLimiter rateLimiter = RateLimiter.create(5);
        List<Runnable> tasks = new ArrayList<Runnable>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new UserRequest(i));
        }
        //ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Runnable runnable : tasks) {
            System.out.println("等待时间：" + rateLimiter.acquire());
            CompletableFuture.runAsync(runnable);
        }
        System.out.println("----------");
        while (true);
    }

    public  static class UserRequest implements Runnable {
        private int id;

        public UserRequest(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int i1 = RandomUtil.randomInt(1, 5);
            try {
                TimeUnit.SECONDS.sleep(i1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(id);
        }
    }
}