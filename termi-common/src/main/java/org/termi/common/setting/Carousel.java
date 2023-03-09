package org.termi.common.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Color;
import org.termi.common.annotation.form.HtmlEditor;
import org.termi.common.annotation.form.Image;

@Data
@Form
@NoArgsConstructor
@AllArgsConstructor
public class Carousel {
    @Color
    @Input(label = "Background color", group = "Item 1")
    private String item1BgColor;
    @Image
    @Input(label = "Background image", group = "Item 1")
    private String item1BgImage;
    @HtmlEditor
    @Input(label = "Html code", group = "Item 1")
    private String item1HtmlCode;
    @Input(label = "Enabled?", group = "Item 1")
    private boolean item1Enabled;

    @Color
    @Input(label = "Background color", group = "Item 2")
    private String item2BgColor;
    @Image
    @Input(label = "Background image", group = "Item 2")
    private String item2BgImage;
    @HtmlEditor
    @Input(label = "Html code", group = "Item 2")
    private String item2HtmlCode;
    @Input(label = "Enabled?", group = "Item 2")
    private boolean item2Enabled;

    @Color
    @Input(label = "Background color", group = "Item 3")
    private String item3BgColor;
    @Image
    @Input(label = "Background image", group = "Item 3")
    private String item3BgImage;
    @HtmlEditor
    @Input(label = "Html code", group = "Item 3")
    private String item3HtmlCode;
    @Input(label = "Enabled?", group = "Item 3")
    private boolean item3Enabled;
}
