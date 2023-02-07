package org.termi.common.widget.widgets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.termi.common.entity.Product;
import org.termi.common.query.ProductQuery;
import org.termi.common.service.ProductService;
import org.termi.common.util.JsonUtil;
import org.termi.common.util.TemplateUtil;
import org.termi.common.widget.WidgetRender;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryWidgetRenderer implements WidgetRender {
    @Autowired
    private ProductService productService;

    @Override
    @Nullable
    public Class<?> getConfigurationClass() {
        return CategoryWidgetConfiguration.class;
    }

    @Override
    public String render(String json, HttpServletRequest request, Model model) {
        CategoryWidgetConfiguration config = JsonUtil.parse(json, CategoryWidgetConfiguration.class);
        if (Objects.isNull(config)) {
            config = new CategoryWidgetConfiguration(10);
        }

        Long categoryId = Long.parseLong(Objects.requireNonNull(model.getAttribute("categoryId")).toString());

        int pageNumber = Optional.ofNullable(request.getParameter("page"))
                .map(Integer::parseInt)
                .orElse(0);
        int sizeSize = config.pageSize();
        Pageable pageable = Pageable.ofSize(sizeSize).withPage(pageNumber);

        Page<Product> page = productService.getList(pageable, new ProductQuery(categoryId));
        Map<String, Object> variables = model.asMap();
        variables.put("config", config);
        variables.put("page", page);

        return TemplateUtil.classpathRender("category", variables);
    }
}
