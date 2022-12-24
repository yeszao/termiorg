package org.termi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.termi.common.entity.Widget;
import org.termi.common.entity.WidgetInstance;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WidgetForm extends Widget {
    private HtmlForm htmlForm;
    private WidgetInstance widgetInstance;

    public WidgetForm(Widget widget, WidgetInstance widgetInstance, HtmlForm htmlForm) {
            BeanUtils.copyProperties(widget, this);
            this.setHtmlForm(htmlForm);
            this.setWidgetInstance(widgetInstance);
    }
}