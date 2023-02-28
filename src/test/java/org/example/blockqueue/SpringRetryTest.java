package org.example.blockqueue;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.JucDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author arthur
 */
@Slf4j
@Import(SpringRetryTest.RetryConfig.class)
@SpringBootTest(classes = JucDemoApplication.class)
@RunWith(SpringRunner.class)
public class SpringRetryTest {

    @Resource
    private RetryTemplate retryTemplate;

    @Test
    public void run() {
        List<Boolean> resultList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                boolean result = retryMethod(i);
                resultList.add(result);
                log.info("Request Result={}", result);
            } catch (Throwable e) {
                log.error("Request Exception - message:{}", e.getMessage());
            }
        }
        log.info("Request ResultList={}", resultList);
    }

    @SuppressWarnings("UnstableApiUsage")
    @SneakyThrows
    @Test
    public void test() {
        StopWatch sw = new StopWatch("test");
        sw.start("request");
        ExecutorService delegate = Executors.newFixedThreadPool(5);
        ListeningExecutorService listeningDecorator = MoreExecutors.listeningDecorator(delegate);
        List<ListenableFuture<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            futureList.add(listeningDecorator.submit(() -> {
                boolean result = false;
                try {
                    result = retryMethod(finalI);
                } catch (Throwable e) {
                    log.error("Request Exception - message:{}", e.getMessage());
                }
                return result;
            }));
        }
        List<Boolean> results = Futures.allAsList(futureList).get();
        sw.stop();
        log.info("Request ResultList={}，cost time {}ms ", results, sw.getTotalTimeMillis());
        sw.start("task1");
        // do something
        Thread.sleep(100);
        sw.stop();
        sw.start("task2");
        // do something
        Thread.sleep(200);
        sw.stop();
        log.info("计算得分完成，耗时：{}",sw.prettyPrint());
    }


    private boolean retryMethod(int requestId) throws Throwable {
        return retryTemplate.execute(context -> {
            log.info("Processing request - Param={} - Retry: count={}", requestId, context.getRetryCount());
            if (requestId % 2 == 0 && context.getRetryCount() < 2) {
                log.error("Request Exception - Param={} - Retry: count={}", requestId, context.getRetryCount());
                //TODO 业务逻辑处理
                throw new RuntimeException("Request Exception");
            }
            return true;
        }, context -> {
            log.info("Recovering request - Param={} - Retry: count={}", requestId, context.getRetryCount());
            //TODO 错误逻辑处理
            return false;
        });
    }


    @TestConfiguration
    static class RetryConfig {
        /**
         * 多少毫秒以后重试
         */
        private Long backOffPeriod = 500L;
        /**
         * 重试次数
         */
        private Integer maxAttempts = 3;

        @Bean
        public RetryTemplate retryTemplate() {
            RetryTemplate retryTemplate = new RetryTemplate();
            //定义重试时间
            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(backOffPeriod);
            retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
            //定义重试次数
            SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
            retryPolicy.setMaxAttempts(maxAttempts);
            retryTemplate.setRetryPolicy(retryPolicy);
            return retryTemplate;
        }
    }
}
