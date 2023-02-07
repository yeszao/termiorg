package org.termi.common.widget.widgets;

import org.termi.common.annotation.Form;

@Form
public record CategoryWidgetConfiguration(
        int pageSize
) {
}