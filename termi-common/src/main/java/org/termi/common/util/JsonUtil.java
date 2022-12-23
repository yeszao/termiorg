package org.termi.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@UtilityClass
public class JsonUtil {
    public static <T> T parse(String str, Class<T> clazz) {
        if (Objects.isNull(str) || Objects.isNull(clazz)) {
            log.warn("Given string or clazz is empty, {}, {}", str, clazz);
            return null;
        }

        try {
            return getMapper().readValue(str, clazz);
        } catch (JsonProcessingException e) {
            log.error("Can not convert string to object, {}, {}", str, clazz);
            e.printStackTrace();
            return null;
        }
    }

    public static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static String objectToString(Object obj) {
        String result = null;

        try {
            result = getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Can not convert object to json string, {}", obj.toString());
        }

        return result;
    }

    public static <T> T fromResourceFile(String filename, TypeReference<T> valueTypeRef) throws IOException {
        Assert.notNull(filename, "Filename can not be null");
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            return getMapper().readValue(inputStream, valueTypeRef);
        }
    }

    public static  <T> T fromResourceFile(String filename, Class<T> clazz) throws IOException {
        Assert.notNull(filename, "Filename can not be null");
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            return getMapper().readValue(inputStream, clazz);
        }
    }
}