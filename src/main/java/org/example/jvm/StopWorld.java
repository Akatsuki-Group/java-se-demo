package org.example.jvm;

import java.util.LinkedList;
import java.util.List;

/**
 * 使用JDK16测试STW的差异
 * 默认G1： -Xmx2g -XX:+PrintGCDetails
 * ZGC：-XX:+UseZGC -Xmx2g -XX:+PrintGCDetails
 * PS： -XX:+UseParallelGC -Xmx2g -XX:+PrintGCDetails
 */
public class StopWorld {
    /*不停往list中填充数据*/
    //就使用不断的填充 堆 -- 触发GC
    public static class FillListThread extends Thread{
        List<byte[]> list = new LinkedList<>();
        @Override
        public void run() {
            try {
                while(true){
//                    if(list.size()*512/1024/1024>=990){
//                       list.clear();
//                       System.out.println("list is clear");
//                    }
                    byte[] bl;
                    for(int i=0;i<100;i++){
                        bl = new byte[512];
                        list.add(bl);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
    public static void main(String[] args) {
        FillListThread myThread = new FillListThread(); //造成GC，造成STW
        myThread.start();
    }
}
