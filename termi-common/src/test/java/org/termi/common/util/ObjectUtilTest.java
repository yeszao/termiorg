package org.termi.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.Map;

class ObjectUtilTest {

    public static class TestClass {
        @Size(min = 10, max = 100)
        int size;
    }

    @Test
    void annotationToMap() {
        Field[] fields = TestClass.class.getDeclaredFields();
        for (Field field: fields) {
            Map<String, Object> map = ObjectUtil.annotationToMap(field, Size.class);
            Assertions.assertEquals(5, map.size());
        }
    }
}