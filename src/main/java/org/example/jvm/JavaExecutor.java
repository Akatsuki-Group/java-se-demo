package org.example.jvm;

import java.util.List;
/**
 * @author King老师
 *  模拟JVM中的解释执行（JVM中的是C++）
 */
public class JavaExecutor {

    public static void main(String[] args) {
    }
    //模拟JVM中的解释器
    public void executor_method(List listBytecode) {//字节码列表
        for (int i=0;i<listBytecode.size();i++) {
            String code =listBytecode.get(i).toString();
            switch (code){
                case "new": //字节码new指令
                    //调用硬编码  0110101  汇编码
                    break;
                case "iconst_1"://字节码iconst指令
                    //调用硬编码
                    break;
                case "istore_1"://字节码istore指令
                    //调用硬编码
                    break;
                case "iload_1"://字节码iload指令
                    //调用硬编码
                    break;
                case "return":
                    //调用硬编码
                    break;
                    //................
            }

        }

    }
}
