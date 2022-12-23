package org.termi.common.dto;

import org.termi.common.enumeration.FormType;

public record TableColumn(
        String columnName,
        String label,
        FormType formType,
        String linkUrl,
        String linkParamKey,
        boolean indent,
        int grid
) {
}