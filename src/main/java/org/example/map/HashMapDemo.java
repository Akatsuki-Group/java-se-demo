package org.example.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tian
 */
public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, Object> hashMap = new HashMap<>();
        Object value1 = hashMap.put("k1", "v1");
        System.out.println(value1);
        Object value2 = hashMap.put("k1", "v2");
        System.out.println(value2);

        System.out.println(1 << 30);
    }
}
