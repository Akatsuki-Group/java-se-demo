package org.example.jvm;
/**
 * @author  King老师
 * C2的BUG
 * -Xint  参数强制虚拟机运行于只有解释器的编译模式
 */
public class C2Bug {
    public void test() {

        int i = 8;
        while ((i -= 3) > 0);
        System. out .println("i = " + i);
    }
    public static void main(String[] args) {
        C2Bug c2bug = new C2Bug();
        for (int i = 0; i < 50_000; i++) {//5万次   前1万次  不会触发JIT及时编译（没有性能优化）、后4次JIT
            c2bug.test();
        }
    }
}
