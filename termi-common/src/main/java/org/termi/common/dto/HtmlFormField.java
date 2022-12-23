package org.termi.common.dto;

import org.termi.common.enumeration.FormType;

public record HtmlFormField(
        String columnName,
        String label,
        boolean required,
        Object value,
        FormType formType,
        String group,
        int order,
        boolean disabled,
        byte grid,
        Object extra
) {
}