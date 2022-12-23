package org.termi.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.termi.common.TestConstants;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassUtilTest {

    String globalExpectedFile = TestConstants.RESOURCES_PATH + "org/termi/common/util/ClassUtil/toMap_globalExpected.json";
    String prefixExpectedFile = TestConstants.RESOURCES_PATH + "org/termi/common/util/ClassUtil/toMap_AAPrefixExpected.json";

    static class TestingEndpoint {
        public static final String A = null;
        public static final String AA = "aa";
        public static final String AA_ = "aa_";
        public static final String AA_BB = "aabb";
        public static final String AA_BB_CC = "aabbcc";
        public static final String AA_BB_CC_DD = "aabbccdd";
        public static final String BB_ = "bb_";
    }

    @Test
    void toMap() throws IOException {
        Map<String, String> expected1 = FileUtil.getObjectFromFile(globalExpectedFile, new TypeReference<>() {});
        Map<String, String> actual1 = ClassUtil.toMap(TestingEndpoint.class, String.class);
        assertEquals(expected1, actual1);

        Map<String, String> expected2 = FileUtil.getObjectFromFile(prefixExpectedFile, new TypeReference<>() {});
        Map<String, String> actual2 = ClassUtil.toMap(TestingEndpoint.class, String.class, "AA_");
        assertEquals(expected2, actual2);
    }

}