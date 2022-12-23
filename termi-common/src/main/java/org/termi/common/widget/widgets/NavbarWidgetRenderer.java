package org.termi.common.widget.widgets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.termi.common.dto.MenuDto;
import org.termi.common.dto.TreeTable;
import org.termi.common.repository.MenuRepository;
import org.termi.common.util.JsonUtil;
import org.termi.common.util.TemplateUtil;
import org.termi.common.widget.WidgetRender;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class NavbarWidgetRenderer implements WidgetRender {
    @Autowired
    private MenuRepository menuRepository;

    @Nullable
    @Override
    public Class<?> getConfigurationClass() {
        return NavbarWidgetConfiguration.class;
    }

    @Override
    public String render(String json, HttpServletRequest request, Model model) {
        NavbarWidgetConfiguration config = JsonUtil.parse(json, NavbarWidgetConfiguration.class);
        if (Objects.isNull(config)) {
            config = new NavbarWidgetConfiguration();
        }

        List<MenuDto> categoryDtoList = MenuDto.fromEntities(menuRepository.findAll());
        TreeTable tree = TreeTable.fromTree(MenuDto.treeing(categoryDtoList), MenuDto.class);

        Map<String, Object> variables = model.asMap();
        variables.put("config", config);
        variables.put("tree", tree);

        return TemplateUtil.classpathRender("main-nav", variables);
    }
}
