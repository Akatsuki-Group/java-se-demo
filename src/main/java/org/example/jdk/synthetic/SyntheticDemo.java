package org.example.jdk.synthetic;

import java.lang.reflect.*;

/**
 * Synthetic 关键字
 * 合成的，由java编译器在编译阶段生成的【构造】
 * JLS: 所有存在于字节码文件中，但是不存在于源文件中的【构造】，都应该由 Synthetic 关键字标记。
 * 【构造】 =》 Constructs => Fields 、Methods 、Constructors
 *
 * @author yuancetian
 */
public class SyntheticDemo {
    public static void main(String[] args) {
        fieldDemo(); //this$0 true
        sayHello();
        methodDemo();
        constructorDemo();
    }

    public static void fieldDemo() {
        Field[] fields = FieldDemo.FieldDemoInner.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + " " + field.isSynthetic());
        }
    }

    public static void constructorDemo() {
        Constructor[] constructors = ConstructorDemo.ConstructorDemoInner.class.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor.getName() + " " + constructor.isSynthetic());
            System.out.println(constructor.getModifiers());
            //modifiers=4096 代表 Synthetic
            System.out.println(Modifier.toString(constructor.getModifiers()));
        }
    }


    public static void methodDemo() {
        Method[] methods = MethodDemo.MethodDemoInner.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " " + method.isSynthetic());
        }
    }

    public static void sayHello() {
        FieldDemo fieldDemo = new FieldDemo();
        System.out.println(fieldDemo.sayHello());
        System.out.println(fieldDemo.new FieldDemoInner().hello());
    }
}
