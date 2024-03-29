package org.example.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 微信公众号：程序员路人，个人博客：http://www.itsoku.com
 */
public class Demo6 {

    //创建一个操作Thread中存放请求任务追踪id口袋的对象,子线程可以继承父线程中内容
    static TransmittableThreadLocal<String> traceIdKD = new TransmittableThreadLocal<>();

    static AtomicInteger threadIndex = new AtomicInteger(1);
    //创建处理请求的线程池子
    static ThreadPoolExecutor disposeRequestExecutor = new ThreadPoolExecutor(3,
            3,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("disposeRequestThread-" + threadIndex.getAndIncrement());
                return thread;
            });

    //记录日志
    public static void log(String msg) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        //获取当前线程存放tranceId口袋中的内容
        String traceId = traceIdKD.get();
        System.out.println("****" + System.currentTimeMillis() + "[traceId:" + traceId + "],[线程:" + Thread.currentThread().getName() + "]," + stack[1] + ":" + msg);
    }

    //模拟controller
    public static void controller(List<String> dataList) {
        log("接受请求");
        service(dataList);
    }

    //模拟service
    public static void service(List<String> dataList) {
        log("执行业务");
        dao(dataList);
    }

    //模拟dao
    public static void dao(List<String> dataList) {
        CountDownLatch countDownLatch = new CountDownLatch(dataList.size());

        log("执行数据库操作");
        String threadName = Thread.currentThread().getName();
        //模拟插入数据
        for (String s : dataList) {
            new Thread(() -> {
                try {
                    //模拟数据库操作耗时100毫秒
                    TimeUnit.MILLISECONDS.sleep(100);
                    log("插入数据" + s + "成功,主线程：" + threadName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        //等待上面的dataList处理完毕
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        test();
//        test1();
        test2();
    }

    public static void test() {
        //需要插入的数据
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dataList.add("数据" + i);
        }

        //模拟5个请求
        int requestCount = 5;
        for (int i = 0; i < requestCount; i++) {
            String traceId = String.valueOf(i);
            disposeRequestExecutor.execute(() -> {
                //把traceId放入口袋中
                traceIdKD.set(traceId);
                try {
                    controller(dataList);
                } finally {
                    //将tranceId从口袋中移除
                    traceIdKD.remove();
                }
            });
        }

        disposeRequestExecutor.shutdown();
    }

    public static void test1() throws InterruptedException {
        //单一线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //InheritableThreadLocal存储
        InheritableThreadLocal<String> username = new InheritableThreadLocal<>();
        for (int i = 0; i < 10; i++) {
            username.set("公众号：码猿技术专栏—"+i);
            Thread.sleep(3000);
            CompletableFuture.runAsync(()-> System.out.println(username.get()),executorService);
        }
    }

    public static void test2() throws Exception {
        //单一线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //需要使用TtlExecutors对线程池包装一下
        executorService = TtlExecutors.getTtlExecutorService(executorService);
        //TransmittableThreadLocal创建
        TransmittableThreadLocal<String> username = new TransmittableThreadLocal<>();
        for (int i = 0; i < 10; i++) {
            username.set("公众号：码猿技术专栏—" + i);
            Thread.sleep(3000);
            CompletableFuture.runAsync(() -> System.out.println(username.get()), executorService);
        }
    }
}