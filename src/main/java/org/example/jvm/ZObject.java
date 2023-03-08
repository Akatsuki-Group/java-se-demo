package org.example.jvm;

public class ZObject {
    ZObject instance = null;

    public  void ReadBarrier(){//读屏障演示 --方法在ZGC中运行。
        ZObject A = new ZObject();//A是局部变量：GCRoots
        A.instance= new ZObject();//A->B
        System.gc();//todo 这里发生一次ZGC， 因为并发转移 B的指针没修正
        //如果代码运行到从堆中读取引用，比如读到B
        Object D =  A.instance; //todo JVM触发读屏障代码：修正B的指针(同时从转发表去掉)4%开销
    }



















    public static void main(String[] args) {
        Object o = new Object();//0  -》13号地址 -》 66号地址
        //GC开始，对象移动开始

        Object o2 =o;//o2  13
        o.hashCode();// 空指针异常

        //GC结束，对象移动结束
    }



}
