package org.example.easyrule;

public class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void dosomeThing() throws Exception {

        if (this.age < 26) {
            throw new Exception("age is too small");
        }
        if (this.name.length() < 10) {
            throw new Exception("some wrong");
        }
    }

    public void setAge(int age) {
        this.age = age;
    }
}