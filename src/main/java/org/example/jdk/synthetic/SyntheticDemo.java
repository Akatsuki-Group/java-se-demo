package org.example.jdk.synthetic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        //sayHello();
        methodDemo();
    }

    public static void fieldDemo() {
        Field[] fields = FieldDemo.FieldDemoInner.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + " " + field.isSynthetic());
        }
    }

    public static void methodDemo() {
        Method[] methods = MethodDemo.MethodDemoInner.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " " + method.isSynthetic());
        }
    }

    public static void sayHello() {
        try {
            Method hello = FieldDemo.FieldDemoInner.class.getMethod("hello");
            Constructor<FieldDemo.FieldDemoInner> constructor = FieldDemo.FieldDemoInner.class.getConstructor();
            FieldDemo.FieldDemoInner fieldDemoInner = constructor.newInstance();
            System.out.println(hello.invoke(fieldDemoInner));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
