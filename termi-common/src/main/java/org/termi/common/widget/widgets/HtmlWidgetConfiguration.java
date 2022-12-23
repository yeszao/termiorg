package org.termi.common.widget.widgets;

import org.termi.common.annotation.Form;
import org.termi.common.annotation.form.HtmlEditor;

@Form
public record HtmlWidgetConfiguration(
        @HtmlEditor
        String html
) {
        public HtmlWidgetConfiguration() {
                this("");
        }
}
