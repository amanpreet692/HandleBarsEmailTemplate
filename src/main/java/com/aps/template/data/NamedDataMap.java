package com.aps.template.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by APS on 18-08-2018.
 */
public class NamedDataMap {
    static Integer a = 1;
    String name;
    Map<String,Integer> countMap = new HashMap<>();

    public NamedDataMap() {
        name = "aps" + (a++);
        countMap.put("num" + (a),a++);
        countMap.put("num" + (a),a++);
    }


    public String getName() {
        return name;
    }

    public Map<String, Integer> getCountMap() {
        return countMap;
    }
}
