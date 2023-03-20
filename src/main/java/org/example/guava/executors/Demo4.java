package org.example.guava.executors;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 微信公众号：程序员路人，个人博客：http://www.itsoku.com
 */
@Slf4j
public class Demo4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("star");
            ExecutorService delegate = Executors.newFixedThreadPool(5);
        try {
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(delegate);
            List<ListenableFuture<Integer>> futureList = new ArrayList<>();
            for (int i = 5; i >= 0; i--) {
                int j = i;
                futureList.add(executorService.submit(() -> {
                    if(j%2==0){
                        throw new RuntimeException("第"+j+"个线程发生异常了");
                    }
                    TimeUnit.SECONDS.sleep(j);
                    return j;
                }));
            }
            List<Object> successList = new ArrayList<>();
            List<Object> failList = new ArrayList<>();

            for (ListenableFuture<Integer> integerListenableFuture : futureList) {
                Futures.addCallback(integerListenableFuture, new FutureCallback<Integer>() {
                    @Override
                    public void onSuccess(@Nullable Integer result) {
                        log.info("{}", result);
                        successList.add(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        log.error("{}", t.getMessage());
                        failList.add(t.getMessage());
                    }
                }, MoreExecutors.directExecutor());
            }
           Futures.whenAllComplete(futureList).run(() -> {
               log.info("所有任务执行完成");
               log.info("成功的任务有:{}", successList);
               log.info("失败的任务有:{}", failList);
           }, MoreExecutors.directExecutor()).get();
            //ListenableFuture<List<Integer>> listListenableFuture = Futures.allAsList(futureList);
            //Futures.addCallback(listListenableFuture, new FutureCallback<List<Integer>>() {
            //    @Override
            //    public void onSuccess(@Nullable List<Integer> result) {
            //        log.info("result中所有结果之和：" + result.stream().reduce(Integer::sum).get());
            //    }
            //
            //    @Override
            //    public void onFailure(Throwable t) {
            //        log.error("执行任务发生异常:" + t.getMessage(), t);
            //    }
            //}, MoreExecutors.directExecutor());
        } finally {
            delegate.shutdown();
        }
        log.info("end");
    }
}