package org.termi.common.widget.widgets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.termi.common.repository.CategoryRepository;
import org.termi.common.util.JsonUtil;
import org.termi.common.util.TemplateUtil;
import org.termi.common.widget.WidgetRender;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TopCategoriesWidgetRenderer implements WidgetRender {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Nullable
    public Class<?> getConfigurationClass() {
        return TopCategoriesWidgetConfiguration.class;
    }

    @Override
    public String render(String json, HttpServletRequest request, Model model) {
        TopCategoriesWidgetConfiguration config = JsonUtil.parse(json, TopCategoriesWidgetConfiguration.class);
        if (Objects.isNull(config)) {
            config = new TopCategoriesWidgetConfiguration();
        }

        //Set<Long> categoryIds = StringUtil.commaToLongSet(config.categoryIds());
        List<Long> categoryIds = config.categoryIds();
        Map<String, Object> variables = model.asMap();
        if (categoryIds.isEmpty()) {
            variables.put("categories", new ArrayList<>());
        } else {
            variables.put("categories", categoryRepository.findAllByIdIn(categoryIds));
        }
        variables.put("config", config);

        return TemplateUtil.classpathRender("top-categories", variables);
    }
}
