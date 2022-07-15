package org.example.jdk.nbac;

import java.util.Arrays;

/**
 * NBAC => Nested Base Access Control 基于嵌套类的访问控制
 * <p>
 * Inner => nestHost => Outer
 * Outer => nestMembers => Inner
 *
 * @author yuancetian
 */
public class NbacDemo {
    public static void main(String[] args) throws Exception {
        new Outer().new Inner().innerPublic();
        new Outer().outerPublic();
    }

    //public static void main(String[] args) {
    //    System.out.println("Inner 的嵌套宿主：" + Outer.Inner.class.getNestHost().getName());
    //    System.out.println("Outer 的嵌套宿主：" + Outer.class.getNestHost().getName());
    //    System.out.println("Outer 的嵌套成员：\n" + Arrays.toString(Outer.class.getNestMembers()));
    //    System.out.println("Inner 的嵌套成员：\n" + Arrays.toString(Outer.Inner.class.getNestMembers()));
    //
    //    System.out.println("Inner 和 Outer 是 nestMate吗？" + Outer.Inner.class.isNestmateOf(Outer.class));
    //    System.out.println("Inner 和 Person 是 nestMate吗？" + Outer.Inner.class.isNestmateOf(Person.class));
    //}
}
