package org.termi.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
}