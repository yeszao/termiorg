package org.termi.common.widget.widgets;

import org.termi.common.annotation.Form;
import org.termi.common.annotation.form.Color;
import org.termi.common.annotation.form.HtmlEditor;
import org.termi.common.annotation.form.Image;

@Form
public record SliderWidgetConfiguration(
        boolean enabled1,
        @Image
        String bgImage1,
        @Color
        String bgColor1,
        @HtmlEditor
        String html1,

        boolean enabled2,
        @Image
        String bgImage2,
        @Color
        String bgColor2,
        @HtmlEditor
        String html2,

        boolean enabled3,
        @Image
        String bgImage3,
        @Color
        String bgColor3,
        @HtmlEditor
        String html3
) {
    public SliderWidgetConfiguration() {
        this(false, "", "", "",
                false, "", "", "",
                false, "", "", "");
    }
}
