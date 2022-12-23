package org.termi.common.util;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassUtil {
    public static Map<String, String> toMap(Class<?> clazz) {
        return toMap(clazz, String.class, "");
    }

    public static Map<String, String> toMap(Class<?> clazz, String prefix) {
        return toMap(clazz, String.class, prefix);
    }

    public static <T> Map<String, T> toMap(Class<?> clazz, Class<T> mapValueClazz) {
        return toMap(clazz, mapValueClazz, "");
    }

    public static <T> Map<String, T> toMap(Class<?> clazz, Class<T> mapValueClazz, String prefix) {
        Assert.notNull(prefix, "Prefix cannot be null");
        Assert.notNull(clazz, "Class cannot be null");

        Map<String, T> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            String name = f.getName();
            if (name.length() < prefix.length()) {
                continue;
            }

            // filter name with start with "prefix"
            boolean sameLength = (name.length() == prefix.length());
            String namePrefix = sameLength ? name : name.substring(0, prefix.length());
            if (!prefix.equals(namePrefix)) {
                continue;
            }

            String key = name.substring(prefix.length());
            try {
                Object value = f.get(null);
                if (mapValueClazz.isInstance(value)) {
                    map.put(key, mapValueClazz.cast(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }
}