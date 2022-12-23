package org.termi.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class StringUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String camelToReadableName(String str) {
        return StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(str),
                StringUtils.SPACE);
    }
}