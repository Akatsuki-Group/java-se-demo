package org.example.jvm;
/**
 * @author  King老师
 * 标量替换
 *
 * -XX:+DoEscapeAnalysis开启逃逸分析（jdk1.8默认开启）
 * -XX:-DoEscapeAnalysis 关闭逃逸分析
 *
 * -XX:+EliminateAllocations开启标量替换（jdk1.8默认开启）
 * -XX:-EliminateAllocations 关闭标量替换
 */
public class VariableDemo {

    public void foo() {
        Teacher teacher = new Teacher();
        teacher.name = "king";
        teacher.age = 18;
        //to do something
    }

    public void foo1() {
        String name = "king";
        int age = 18;
        //to do something
    }

}

class Teacher{
    String name;
    String sexType;
    int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
