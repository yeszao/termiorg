package org.termi.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class StringUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String camelToReadableName(String str) {
        return StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(str),
                StringUtils.SPACE);
    }

    public static Set<Long> commaToLongSet(@Nullable String str) {
        return org.springframework.util.StringUtils.commaDelimitedListToSet(str)
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }
}