package org.termi.common.widget.widgets;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.termi.common.util.JsonUtil;
import org.termi.common.util.TemplateUtil;
import org.termi.common.widget.WidgetRender;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Service
public class SliderWidgetRenderer implements WidgetRender {
    @Nullable
    @Override
    public Class<?> getConfigurationClass() {
        return SliderWidgetConfiguration.class;
    }

    @Override
    public String render(String json, HttpServletRequest request, Model model) {
        SliderWidgetConfiguration config = JsonUtil.parse(json, SliderWidgetConfiguration.class);
        if (Objects.isNull(config)) {
            config = new SliderWidgetConfiguration();
        }

        Map<String, Object> variables = model.asMap();
        variables.put("config", config);

        return TemplateUtil.classpathRender("slider", variables);
    }
}
