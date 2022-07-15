package org.example.jdk.synthetic;

/**
 * @author yuancetian
 */
public class FieldDemo {

    public String sayHello() {
        return "FieldDemo Hello";
    }

    class FieldDemoInner {
        public String hello() {
            return "FieldDemoInner " + sayHello();
        }
    }
}
