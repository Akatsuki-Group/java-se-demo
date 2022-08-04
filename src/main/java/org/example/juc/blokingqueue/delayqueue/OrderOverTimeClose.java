package org.example.juc.blokingqueue.delayqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 使用延时队列DelayQueue实现订单超时关闭
 * 后台守护线程不断的执行检测工作
 */

public class OrderOverTimeClose {

    private volatile static OrderOverTimeClose oderOverTimeClose = null;

    private OrderOverTimeClose() {

    }

    /**
     * 单例模式，双检查锁模式，在并发环境下对象只被初始化一次
     */
    public static OrderOverTimeClose getInstance() {
        if (oderOverTimeClose == null) {
            synchronized (OrderOverTimeClose.class) {
                if (oderOverTimeClose == null) {
                    oderOverTimeClose = new OrderOverTimeClose();
                }
            }
        }
        return oderOverTimeClose;
    }

    /**
     * 守护线程
     */
    private Thread mainThread;

    /**
     * 启动方法
     */
    public void init() {
        mainThread = new Thread(() -> execute());
        mainThread.setDaemon(true);
        mainThread.setName("守护线程-->");
        mainThread.start();
    }

    /**
     * 创建空延时队列
     */
    private DelayQueue<OrderInfo> queue = new DelayQueue<OrderInfo>();

    /***
     * 批量操作的最大条数
     */
    private static int MAX_BATCH_SIZE = 10;

    /**
     * 等徒时长
     */
    private static long MAX_WAIT_TIME = 700;

    /**
     * 读取延时队列，关闭超时订单
     */
    private void execute() {
        OrderInfo orderInfo1;
        List<OrderInfo> willSend;
        while (true) {
            try {
                //从队列里获取超时的订单
                willSend = new ArrayList<>(10);
                OrderInfo orderInfo = queue.take();
                willSend.add(orderInfo);
                int i = 0;
                while (++i < MAX_BATCH_SIZE &&
                        (orderInfo1 = queue.poll(MAX_WAIT_TIME, TimeUnit.MILLISECONDS)) != null) { // poll是一个等待的操作，当等
                    willSend.add(orderInfo1);                                                     // 待 MAX_WAIT_TIME 后未能获
                }
                send(willSend);
                //// 检查订单状态，是否已经成功，成功则将订单从队列中删除。
                //if (Objects.equals(orderInfo.getStatus(), "成功")) {
                //    //TODO 支付成功的订单处理逻辑
                //    System.out.println("线程：" + Thread.currentThread().getName() + "，订单号："
                //            + orderInfo.getOrderNo() + "，订单状态："
                //            + orderInfo.getStatus() + "，订单创建时间："
                //            + orderInfo.getCreateTime()
                //            + "，订单超时时间：" + orderInfo.getExpTime() + "，当前时间："
                //            + OrderPay.getTime(System.currentTimeMillis()));
                //    Thread.sleep(2000);
                //} else {
                //    // TODO 超时未支付订单处理逻辑
                //    System.out.println("线程：" + Thread.currentThread().getName() + "，订单号："
                //            + orderInfo.getOrderNo() + "，变更订单状态为：超时关闭"
                //            + "，订单创建时间："
                //            + orderInfo.getCreateTime()
                //            + "，订单超时时间：" + orderInfo.getExpTime() + "，当前时间："
                //            + OrderPay.getTime(System.currentTimeMillis()));
                //    Thread.sleep(2000);
                //}
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 插入订单到延时队列中
     */
    public void orderPutQueue(OrderInfo orderInfo, String createTime,
                              String overTime) {
        System.out.println("订单号：" + orderInfo.getOrderNo()
                + "，订单状态：" + orderInfo.getStatus()
                + "，订单创建时间：" + createTime
                + "，订单过期时间：" + overTime);
        queue.add(orderInfo);
    }

    protected void send(List<OrderInfo> willSend){
        System.out.println("发送"+willSend.size()+"条消息");
        willSend.forEach(System.out::println);
        System.out.println("=============发送完成========");
    }

}