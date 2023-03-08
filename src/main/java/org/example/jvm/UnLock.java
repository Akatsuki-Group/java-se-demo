package org.example.jvm;
/**
 * @author  King老师
 * 锁消除
 *
 * -XX:+EliminateLocks开启锁消除（jdk1.8默认开启）
 * -XX:-EliminateLocks 关闭锁消除
 */
public class UnLock {
    public static void main(String[] args) {
        //这种之间 性能有没有很大差别，有刷1 没有刷2  ，因为有JIT的锁消除技术
        long timeStart1 = System.currentTimeMillis();
        for(int i=0; i<10000000; i++) {
            BufferString("king","666");//这里调用方法
        }
        long timeEnd1 = System.currentTimeMillis();
        System.out.println("StringBuffer花费的时间" + (timeEnd1 - timeStart1));

        long timeStart2 = System.currentTimeMillis();
        for(int i=0; i<10000000; i++) {
            BuilderString("king","999");
        }
        long timeEnd2 = System.currentTimeMillis();
        System.out.println("StringBuilder花费的时间" + (timeEnd2 - timeStart2));
    }
    public static String BufferString(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        return sb.toString();
    }

    public static String BuilderString(String s1, String s2) {
        StringBuilder sd = new StringBuilder();
        sd.append(s1);
        sd.append(s2);
        return sd.toString();
    }
}
