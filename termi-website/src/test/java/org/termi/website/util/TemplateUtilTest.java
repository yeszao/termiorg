package org.termi.website.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.termi.common.util.TemplateUtil;
import org.termi.website.TestConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TemplateUtilTest {
    String inputFile = TestConstants.RESOURCES_PATH + "org/termi/website/util/TemplateUtil/template.html";
    String expectedFile = TestConstants.RESOURCES_PATH + "org/termi/website/util/TemplateUtil/template-expected.html";

    @Test
    void render() throws IOException {
        String input = Files.readString(Path.of(inputFile), StandardCharsets.UTF_8);
        String expected = Files.readString(Path.of(expectedFile), StandardCharsets.UTF_8);

        Map<String, Object> params = new HashMap<>();
        params.put("title", "Render template");
        params.put("rules", List.of("Rule 1", "Rule 2", "Rule 3"));

        Assertions.assertEquals(expected, TemplateUtil.stringRender(input, params));
    }

    @Test
    void renderByClasspathFile() throws IOException {
        String expected = Files.readString(Path.of(expectedFile), StandardCharsets.UTF_8);

        Map<String, Object> params = new HashMap<>();
        params.put("title", "Render template");
        params.put("rules", List.of("Rule 1", "Rule 2", "Rule 3"));

        Assertions.assertEquals(expected, TemplateUtil.classpathRender("template", params));
    }
}