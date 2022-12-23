package org.termi.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FileUtil {
    private static final ObjectMapper objectMapper = JsonUtil.getMapper();

    public static <T> T getObjectFromFile(String filename, TypeReference<T> valueTypeRef) throws IOException {
        Assert.notNull(filename, "Filename can not be null");
        String content = CharStreams.toString(new FileReader(filename));
        return objectMapper.readValue(content, valueTypeRef);
    }

    public static List<String> getLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    public static String getContentFromFile(String filename) throws IOException {
        return CharStreams.toString(new FileReader(filename));
    }
}