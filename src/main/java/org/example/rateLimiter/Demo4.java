package org.example.rateLimiter;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author arthur
 */
public class Demo4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        map();
    }

    private static void rateLimiter() throws ExecutionException, InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://124.71.38.83:6379").setPassword("tt19950826tt");
        config.useSingleServer().setTimeout(10000);
        RedissonClient client = Redisson.create(config);

        RRateLimiter rateLimiter = client.getRateLimiter("feishu:rate_limiter");
        rateLimiter.trySetRate(RateType.PER_CLIENT, 5, 1, RateIntervalUnit.MINUTES);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ListeningExecutorService executorService1 = MoreExecutors.listeningDecorator(executorService);
        List<ListenableFuture<?>> futureList = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            ListenableFuture<?> submit = executorService1.submit(() -> {
                try {
                    rateLimiter.acquire();
                    System.out.println("线程" + Thread.currentThread().getId() + "进入数据区：" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futureList.add(submit);
        }
        List<@Nullable Object> results = Futures.allAsList(futureList).get();
        long end = System.currentTimeMillis();
        System.out.println("执行后的返回值：" + results + "，耗时：" + (end - start) / 1000 + "秒");
        executorService.shutdown();
        client.shutdown();
    }

    public static void  map(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://124.71.38.83:6379").setPassword("tt19950826tt");
        config.useSingleServer().setTimeout(10000);
        RedissonClient client = Redisson.create(config);
        RMap<Object, Object> testMap = client.getMap("test_map");
        testMap.put("name","arthur");
        testMap.put("age",18);
        testMap.put("user",new User("arthur",18));
        testMap.entrySet().forEach(System.out::println);
        client.shutdown();
    }

    static class User implements Serializable {
        private String name;
        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
