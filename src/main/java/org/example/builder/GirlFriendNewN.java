package org.example.builder;

import lombok.Data;

import java.util.*;

@Data
public class GirlFriendNewN {
    // 省略属性 ...
    private String name;
    private int age;
    private int bust;
    private int waist;
    private int hips;
    private List<String> hobby;
    private String birthday;
    private String address;
    private String mobile;
    private String email;
    private String hairColor;
    private Map<String, String> gift;
    // 省略 getter & setter ...

    // 为了演示方便，加几个聚合方法
    public void addHobby(String hobby) {
        this.hobby = Optional.ofNullable(this.hobby).orElse(new ArrayList<>());
        this.hobby.add(hobby);
    }
    public void addGift(String day, String gift) {
        this.gift = Optional.ofNullable(this.gift).orElse(new HashMap<>());
        this.gift.put(day, gift);
    }
    public void setVitalStatistics(int bust, int waist, int hips) {
        this.bust = bust;
        this.waist = waist;
        this.hips = hips;
    }
    public static void main(String[] args) {
        GirlFriendNewN girlFriendNewN =
                 Builder.of(GirlFriendNewN::new)
                .with(GirlFriendNewN::setName, "小美")
                .with(GirlFriendNewN::setAge, 18)
                .with(GirlFriendNewN::setVitalStatistics, 33, 23, 33)
                .with(GirlFriendNewN::setBirthday, "2001-10-26")
                .with(GirlFriendNewN::setAddress, "上海浦东")
                .with(GirlFriendNewN::setMobile, "18688888888")
                .with(GirlFriendNewN::setEmail, "pretty-xiaomei@qq.com")
                .with(GirlFriendNewN::setHairColor, "浅棕色带点微卷")
                .with(GirlFriendNewN::addHobby, "逛街")
                .with(GirlFriendNewN::addHobby, "购物")
                .with(GirlFriendNewN::addHobby, "买东西")
                .with(GirlFriendNewN::addGift, "情人节礼物", "LBR 1912女王时代")
                .with(GirlFriendNewN::addGift, "生日礼物", "迪奥烈焰蓝金")
                .with(GirlFriendNewN::addGift, "纪念日礼物", "阿玛尼红管唇釉")
                // 等等等等 ...
                .build();
        System.out.println(girlFriendNewN);
    }
}
