package org.termi.common.dto;

import org.termi.common.annotation.TableElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class TreeBaseDto {
    @TableElement(label = "Id")
    private long id;

    @TableElement(label = "Name", indent = true, grid = 4)
    private String name;

    @TableElement(label = "Sort")
    private int sort;

    private long parentId;

    private int level;

    private List<? extends TreeBaseDto> children;

    private boolean hasChild;

    public static <T extends TreeBaseDto> List<T> treeing(List<T> flats) {
        List<T> tree = treeing(0L, 0, flats);
        sortTree(tree);
        return tree;
    }

    public static <T extends TreeBaseDto> List<T> treeing(long parentId, int level, List<T> flats) {
        if (Objects.isNull(flats) || flats.isEmpty()) {
            return Collections.emptyList();
        }

        return flats.stream()
                .filter(t -> t.getParentId() == parentId)
                .peek(
                        t -> {
                            t.setLevel(level);
                            t.setChildren(treeing(t.getId(), level + 1, flats));
                            t.setHasChild(t.getChildren().size() > 0);
                        }
                )
                .collect(Collectors.toList());
    }

    public static <T extends TreeBaseDto> void sortTree(List<T> tree) {
        if (Objects.isNull(tree) || tree.isEmpty()) {
            return;
        }

        tree.sort(Comparator.comparingInt(TreeBaseDto::getSort));
        tree.forEach(t -> sortTree(t.getChildren()));
    }

    public static <T extends TreeBaseDto> List<SelectOption> toSelectOptions(List<T> dtoList, Set<Long> disables) {
        if (dtoList.isEmpty()) {
            return new ArrayList<>();
        }

        List<SelectOption> options = new ArrayList<>();
        for (TreeBaseDto t : dtoList) {
            options.add(SelectOption.of(t.getId(), "&nbsp;".repeat(t.getLevel() * 2) + t.getName(), disables));
            if (t.isHasChild()) {
                options.addAll(toSelectOptions(t.getChildren(), disables));
            }
        }

        return options;
    }

    public static <T extends TreeBaseDto> Set<Long> getChildIdsOf(Collection<Long> targetIds, List<T> dtoList) {
        if (targetIds.isEmpty() || dtoList.isEmpty()) {
            return new HashSet<>();
        }

        Set<Long> allIds = new HashSet<>(targetIds);
        for (TreeBaseDto t : dtoList) {
            if (allIds.contains(t.getParentId())) {
                allIds.add(t.getId());
            }
            allIds.addAll(getChildIdsOf(allIds, t.getChildren()));
        }

        return allIds;
    }
}