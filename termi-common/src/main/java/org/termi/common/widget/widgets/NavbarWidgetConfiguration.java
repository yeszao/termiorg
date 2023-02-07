package org.termi.common.widget.widgets;

import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Image;

@Form
public record NavbarWidgetConfiguration(
        @Image
        @Input(grid = 4)
        String logo
) {
}
