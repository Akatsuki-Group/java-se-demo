package org.example.jvm;
/**
 * @author  King老师
 * 方法内联
 * -XX:+PrintCompilation   //在控制台打印编译过程信息
 * -XX:+UnlockDiagnosticVMOptions //解锁对JVM进行诊断的选项参数。默认是关闭的，开启后支持一些特定参数对JVM进行诊断
 * -XX:+PrintInlining //将内联方法打印出来
 */
public class CompDemo {
    private int add1(int x1, int x2, int x3, int x4) {
        return add2(x1, x2) + add2(x3, x4);//
    }
    private int add2(int x1, int x2) {
        return x1 + x2;
    }
    //内联后的调用类似于以下方法
    private int add(int x1, int x2, int x3, int x4) {
        return x1 + x2+ x3 + x4;
    }

    public static void main(String[] args) {
        CompDemo compDemo = new CompDemo();
        //方法调用计数器的默认阈值10000次，我们循环遍历超过需要阈值
        for(int i=0; i<1000; i++) {
            compDemo.add1(1,2,3,4);
        }

    }
}
