package org.termi.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class TypeUtilTest {

    @Test
    void isNumber() {
        Field[] numberClassFields = NumberClass.class.getDeclaredFields();
        for (Field f: numberClassFields) {
            Assertions.assertTrue(TypeUtil.isNumber(f.getType()), String.format("%s is not a number", f.getName()));
        }

        Field[] notNumberClassFields = NotNumberClass.class.getDeclaredFields();
        for (Field f: notNumberClassFields) {
            Assertions.assertFalse(TypeUtil.isNumber(f.getType()), String.format("%s is a number", f.getName()));
        }
    }

    @Test
    void isBool() {
        Field[] boolClassFields = BooleanClass.class.getDeclaredFields();
        for (Field f: boolClassFields) {
            Assertions.assertTrue(TypeUtil.isBool(f.getType()), String.format("%s is not a number", f.getName()));
        }

        Field[] notBoolClassFields = NotBooleanClass.class.getDeclaredFields();
        for (Field f: notBoolClassFields) {
            Assertions.assertFalse(TypeUtil.isBool(f.getType()), String.format("%s is a number", f.getName()));
        }
    }

    @Test
    void isEnum() {
        Field[] fields = EnumClass.class.getDeclaredFields();
        for (Field f: fields) {
            Assertions.assertTrue(TypeUtil.isEnum(f.getType()), String.format("%s is not a enum", f.getName()));
        }

        Field[] notBoolClassFields = NotBooleanClass.class.getDeclaredFields();
        for (Field f: notBoolClassFields) {
            Assertions.assertFalse(TypeUtil.isEnum(f.getType()), String.format("%s is a enum", f.getName()));
        }
    }
}