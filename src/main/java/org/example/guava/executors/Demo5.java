package org.example.guava.executors;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 微信公众号：程序员路人，个人博客：http://www.itsoku.com
 */
@Slf4j
public class Demo5 {
    static Map<String, TaskResult> taskMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("star");
        ExecutorService delegate = Executors.newFixedThreadPool(5);
        try {
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(delegate);
            List<ListenableFuture<Integer>> futureList = new ArrayList<>();
            for (int i = 5; i >= 0; i--) {
                int j = i;
                Task task = new Task(i, "任务" + i);
                ListenableFuture<Integer> submit = executorService.submit(task);
                taskMap.put(task.getName(), new TaskResult(task.getName()));
                Futures.addCallback(submit, new TaskFutureCallback(task.getName()), MoreExecutors.directExecutor());
                futureList.add(submit);
            }
            Futures.whenAllComplete(futureList).run(() -> {
                log.info("所有任务执行完成");
                taskMap.forEach((k, v) -> {
                    log.info("任务名称:{},任务结果:{}", k, v);
                });
            }, MoreExecutors.directExecutor()).get();
        } finally {
            delegate.shutdown();
        }
        log.info("end");
    }

    public static class TaskFutureCallback implements FutureCallback {
        private String taskName;

        public TaskFutureCallback(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void onSuccess(Object result) {
            log.info("task:{} result: {}", taskName, result);
            taskMap.get(taskName).setResult(result);
        }

        @Override
        public void onFailure(Throwable t) {
            log.error("task:{} error: {}", taskName, t.getMessage());
            taskMap.get(taskName).setThrowable(t);
        }
    }

    public static class Task implements Callable<Integer> {
        private int i;
        private String name;

        public Task(int i, String name) {
            this.i = i;
            this.name = name;
        }

        public int getI() {
            return i;
        }

        public String getName() {
            return name;
        }

        @Override
        public Integer call() throws Exception {
            if (i % 2 == 0) {
                int it = 1 / 0;
            }
            TimeUnit.SECONDS.sleep(i);
            return i;
        }

    }

    public static class TaskResult {
        private String taskName;
        private Object result;
        private Throwable throwable;

        public TaskResult(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public String toString() {
            return "TaskResult{" +
                    "taskName='" + taskName + '\'' +
                    ", result=" + result +
                    ", throwable=" + throwable +
                    '}';
        }
    }
}