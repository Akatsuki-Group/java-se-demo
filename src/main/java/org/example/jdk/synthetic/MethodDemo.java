package org.example.jdk.synthetic;

/**
 * @author yuancetian
 */
public class MethodDemo {

    class MethodDemoInner {
        private String innerName;
    }

    public void setInnerName(String innerName) {
        new MethodDemoInner().innerName = innerName;
    }

    public String getInnerName() {
        return new MethodDemoInner().innerName;
    }
}
