package org.termi.common.widget.widgets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.termi.common.dto.CategoryDto;
import org.termi.common.dto.TreeTable;
import org.termi.common.repository.CategoryRepository;
import org.termi.common.util.TemplateUtil;
import org.termi.common.widget.WidgetRender;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CategorySidebarWidgetRenderer implements WidgetRender {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String render(String configuration, HttpServletRequest request, Model model) {
        List<CategoryDto> categoryDtoList = CategoryDto.fromEntities(categoryRepository.findAll());
        TreeTable tree = TreeTable.fromTree(CategoryDto.treeing(categoryDtoList), CategoryDto.class);

        Map<String, Object> variables = model.asMap();
        variables.put("tree", tree);

        return TemplateUtil.classpathRender("category-sidebar", variables);
    }
}
