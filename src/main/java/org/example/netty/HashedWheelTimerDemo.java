package org.example.netty;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yuancetian
 */
public class HashedWheelTimerDemo {
    private static long start;

    public static void main(String[] args) {
        // 创建Timer, 精度为100毫秒,
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.NANOSECONDS);
        MyTask task1 = new MyTask();
        MyTask task2 = new MyTask();
        MyTask task3 = new MyTask();
        DateTime dateTime = new DateTime();
        start = dateTime.getTime();
        System.out.println(StrUtil.format("添加任务时间【{}】", DateUtil.format(dateTime, "yyyy-MM-dd HH:mm:ss.SSS")));
        Timeout timeout = timer.newTimeout(task1, 5, TimeUnit.SECONDS);
//        boolean cancel = timeout.cancel();
//        if (cancel){
//            System.out.println("取消任务成功");
//        }
        timer.newTimeout(task2, 10, TimeUnit.SECONDS);
        timer.newTimeout(task3, 15, TimeUnit.SECONDS);
        // 阻塞main线程
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 1. taskId taskName createTime nextExecuteTime called success
     *
     *
     *
     */


    static class MyTask implements TimerTask {

        @Override
        public void run(Timeout timeout) throws Exception {
            boolean expired = timeout.isExpired();
            DateTime dateTime = new DateTime();
            long end = dateTime.getTime();
            System.out.println(StrUtil.format("任务在时间【{}】执行了，间隔{}",
                    DateUtil.format(dateTime, "yyyy-MM-dd HH:mm:ss.SSS"),
                    (end - start)
            ));
        }
    }
}
