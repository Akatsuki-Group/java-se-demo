package org.example.builder;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class GirlFriendNew {

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
    // 等等等等 ...
    // 省略 getter & setter ...
    public static void main(String[] args) {
        GirlFriendNew myGirlFriend = new GirlFriendNew();
        myGirlFriend.setName("迪丽热巴");
        myGirlFriend.setAge(18);
        myGirlFriend.setBust(33);
        myGirlFriend.setWaist(23);
        myGirlFriend.setHips(33);
        myGirlFriend.setBirthday("2001-10-26");
        myGirlFriend.setAddress("公众号：woniuxgg");
        myGirlFriend.setMobile("18688888888");
        myGirlFriend.setEmail("pretty-xiaomei@qq.com");
        myGirlFriend.setHairColor("浅棕色带点微卷");
        List<String> hobby = new ArrayList<>();
        hobby.add("逛街");
        hobby.add("购物");
        hobby.add("买东西");
        myGirlFriend.setHobby(hobby);
        Map<String, String> gift = new HashMap<>();
        gift.put("情人节礼物", "LBR 1912女王时代");
        gift.put("生日礼物", "迪奥烈焰蓝金");
        gift.put("纪念日礼物", "阿玛尼红管唇釉");
        myGirlFriend.setGift(gift);
        // 等等等等 ...
    }
}
