package org.example.builder;

import lombok.Data;

@Data
public class GirlFriend {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // 省略 getter & setter ...
    public static void main(String[] args) {
        GirlFriend myGirlFriend = new GirlFriend();
        myGirlFriend.setName("赵丽颖");
        myGirlFriend.setAge(18);
    }
}
