package org.example.jdk.nbac;

import java.lang.reflect.Method;

/**
 * @author yuancetian
 */
public class Outer {

    public void outerPublic() throws Exception {
        System.out.println("Outer Public");
        new Inner().reflectOuter(new Outer());
    }

    private void outerPrivate() {
        System.out.println("Outer Private");
    }

    /**
     * 在内部类中存在同一种方法的不同调用方式所呈现的不同结果的情况
     * 如果调用外部类的private方法
     *      1.直接调用，不报错
     *      2.反射调用，报错
     */
    class Inner {
        public void innerPublic() {
            System.out.println("Inner Public");
            outerPrivate();
        }

        public void reflectOuter(Outer outer) throws Exception {
            Method outerPrivate = outer.getClass().getDeclaredMethod("outerPrivate");
            //outerPrivate.setAccessible(true);
            outerPrivate.invoke(outer);
        }
    }
}
