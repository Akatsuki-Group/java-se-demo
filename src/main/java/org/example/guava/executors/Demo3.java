package org.example.guava.executors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 微信公众号：程序员路人，个人博客：http://www.itsoku.com
 */
@Slf4j
public class Demo3 {
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
            //获取一批任务的执行结果
            List<Integer> resultList = Futures.allAsList(futureList).get();
            //输出
            resultList.forEach(item -> {
                log.info("{}", item);
            });
        } finally {
            delegate.shutdown();
        }
    }
}