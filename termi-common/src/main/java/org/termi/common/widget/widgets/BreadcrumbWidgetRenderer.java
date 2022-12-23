package org.termi.common.widget.widgets;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.termi.common.util.TemplateUtil;
import org.termi.common.widget.WidgetRender;

import javax.servlet.http.HttpServletRequest;

@Service
public class BreadcrumbWidgetRenderer implements WidgetRender {
    @Override
    public String render(String json, HttpServletRequest request, Model model) {
        return TemplateUtil.classpathRender("breadcrumb", model.asMap());
    }
}
