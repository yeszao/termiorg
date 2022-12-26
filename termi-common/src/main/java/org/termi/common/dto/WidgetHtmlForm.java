package org.termi.common.dto;

import org.termi.common.entity.Widget;
import org.termi.common.entity.WidgetInstance;

public record WidgetHtmlForm(
        Widget widget,
        HtmlForm htmlForm,
        WidgetInstance widgetInstance,
        boolean isGlobal,
        boolean allowSaving) {
}