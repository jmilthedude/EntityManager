package net.thedudemc.entitymanager.util;

import java.util.*;

public class Util {
    public static <T> HashMap<T, Integer> sortMap(HashMap<T, Integer> map, boolean descending) {
        List<Map.Entry<T, Integer>> list = new LinkedList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue());
        if (descending) Collections.reverse(list);

        HashMap<T, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<T, Integer> entry : list) {
            temp.put(entry.getKey(), entry.getValue());
        }
        return temp;
    }
}
