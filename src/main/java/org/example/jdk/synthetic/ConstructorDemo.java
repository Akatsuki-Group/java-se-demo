package org.example.jdk.synthetic;

/**
 * @author yuancetian
 */
public class ConstructorDemo {
    public ConstructorDemoInner constructorDemoInner=new ConstructorDemoInner();

    class ConstructorDemoInner {
        private ConstructorDemoInner() {
        }
    }
}
