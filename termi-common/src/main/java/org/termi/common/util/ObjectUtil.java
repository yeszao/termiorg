package org.termi.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ObjectUtil {
    /**
     * Get all fields of one object
     *
     * @param clazz the class of given object
     * @return the list of the fields
     */
    public static List<Field> getObjectFields(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getObjectFields(clazz.getSuperclass()));
        List<Field> filteredFields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        result.addAll(filteredFields);

        return result;
    }

    public static Map<String, Object> annotationToMap(Field field, Class<? extends Annotation> clazz) {
        Annotation a = field.getAnnotation(clazz);
        Method[] fields = clazz.getDeclaredMethods();
        Map<String, Object> map = new HashMap<>();
        for (Method m : fields) {
            try {
                map.put(m.getName(), m.invoke(a));
            } catch (IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException
                    | NullPointerException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }

        }

        return map;
    }
}