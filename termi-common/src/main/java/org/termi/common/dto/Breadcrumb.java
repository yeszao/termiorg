package org.termi.common.dto;

import org.termi.common.widget.component.IconText;

import java.util.List;

public record Breadcrumb(
        String title,
        List<IconText> items
) {
}