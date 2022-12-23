package org.termi.common.widget;

import org.springframework.ui.Model;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

public interface WidgetRender {
    @Nullable
    default Class<?> getConfigurationClass() {
        return null;
    }

    String render(String json, HttpServletRequest request, Model model);
}
