package org.termi.common.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import org.termi.common.util.FileUtil;
import org.termi.common.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.termi.common.TestConstants;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TreeBaseDtoTest {
    List<TreeBaseDto> original = FileUtil.getObjectFromFile(
            TestConstants.RESOURCES_PATH + "org/termi/common/dto/TreeBase/original-flat.json",
            new TypeReference<>() {});

    List<TreeBaseDto> originalTreed = FileUtil.getObjectFromFile(
            TestConstants.RESOURCES_PATH + "org/termi/common/dto/TreeBase/original-treed.json",
            new TypeReference<>() {});

    List<TreeBaseDto> sortedTreed = FileUtil.getObjectFromFile(
            TestConstants.RESOURCES_PATH + "org/termi/common/dto/TreeBase/sorted-treed.json",
            new TypeReference<>() {});

    List<TreeBaseDto> sortedFlatted = FileUtil.getObjectFromFile(
            TestConstants.RESOURCES_PATH + "org/termi/common/dto/TreeBase/sorted-flatted.json",
            new TypeReference<>() {});

    TreeBaseDtoTest() throws IOException {
    }

    @Test
    void treeing() {
        List<? extends TreeBaseDto> actual = TreeBaseDto.treeing(this.original);
        System.out.println(JsonUtil.objectToString(actual));
        assertIterableEquals(originalTreed, actual);
    }

    @Test
    void sortTree() {
        TreeBaseDto.sortTree(this.originalTreed);
        assertIterableEquals(sortedTreed, this.originalTreed);
    }
}