package org.termi.common.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    @SafeVarargs
    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> ...maps) {
        Map<K, V> map = new HashMap<>(map1);
        for (Map<K, V> m: maps) {
            map.putAll(m);
        }
        return map;
    }

    public static String toQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(((Map.Entry<?, ?>) stringObjectEntry).getKey()).append("=").append(((Map.Entry<?, ?>) stringObjectEntry).getValue());
        }

        return sb.toString();
    }
}