package org.termi.common.util;

import java.lang.constant.Constable;
import java.time.temporal.Temporal;
import java.util.Set;

public class TypeUtil {
    static Set<Class<?>> primaryNumberClass = Set.of(
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class
    );

    public static boolean isNumber(Class<?> type) {
        if (Number.class.isAssignableFrom(type)) {
            return true;
        }
        return type.isPrimitive() && primaryNumberClass.contains(type);
    }

    public static boolean isBool(Class<?> type) {
       return Boolean.class.isAssignableFrom(type) || boolean.class.equals(type);
    }

    public static boolean isEnum(Class<?> type) {
        return Enum.class.isAssignableFrom(type);
    }

    public static boolean isChar(Class<?> type) {
        return Character.class.isAssignableFrom(type) || char.class.equals(type);
    }

    public static boolean isTemporal(Class<?> type) {
        return Temporal.class.isAssignableFrom(type);
    }

    public static boolean isEditable(Class<?> type) {
        return isNumber(type) || isBool(type) || isEnum(type) || isChar(type) || isTemporal(type);
    }

    public static boolean isFormable(Class<?> type) {
        return type.isPrimitive() || Constable.class.isAssignableFrom(type);
    }
}
