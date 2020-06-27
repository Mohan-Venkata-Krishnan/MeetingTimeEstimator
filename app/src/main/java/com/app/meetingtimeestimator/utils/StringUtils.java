package com.app.meetingtimeestimator.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StringUtils {

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static HashMap<String, Integer> filterBySize(HashMap<String, Integer> map, int maxSize) {
        int tempCount = 0;
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entrySet : map.entrySet()) {
            if (tempCount < maxSize) {
                temp.put(entrySet.getKey(), entrySet.getValue());
                tempCount++;
            }
        }
        return temp;
    }

}
