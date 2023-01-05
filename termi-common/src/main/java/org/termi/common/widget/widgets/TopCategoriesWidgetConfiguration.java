package org.termi.common.widget.widgets;

import org.termi.common.annotation.Form;
import org.termi.common.annotation.form.Select;

import java.util.ArrayList;
import java.util.List;

import static org.termi.common.constant.AdminEndpoints.CATEGORY_ALL_API;

@Form
public record TopCategoriesWidgetConfiguration(
        String title,

        @Select(url = CATEGORY_ALL_API, multiple = true)
        List<Long> categoryIds
) {
    public TopCategoriesWidgetConfiguration() {
        this("Shop by Category", new ArrayList<>());
    }
}