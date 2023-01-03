package org.termi.website.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.termi.common.configuration.TermiConfig;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.service.LayoutService;
import org.termi.common.service.SettingService;
import org.termi.common.service.WidgetInstanceService;
import org.termi.common.widget.WidgetPosition;
import org.termi.common.widget.WidgetRender;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class BaseController {
    @Autowired
    private SettingService settingService;

    @Autowired
    private TermiConfig termiConfig;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private WidgetInstanceService widgetInstanceService;

    public void renderLayout(String endpointName, Model model) {
        List<WidgetInstance> instances = widgetInstanceService
                .getInstances(List.of(endpointName, "~global~"));

        model.addAttribute("setting", settingService.getAll());
        model.addAttribute("termiConfig", termiConfig);
        Map<WidgetPosition, StringBuilder> rendered = layoutService.group(
                instances,
                StringBuilder::new,
                (StringBuilder s, WidgetRender renderer, WidgetInstance widgetInstance)
                        -> {
                    s.append(renderer.render(widgetInstance.getConfiguration(), request, model));
                }
        );

        // output to html
        for (WidgetPosition position : WidgetPosition.values()) {
            model.addAttribute(position.name(), rendered.get(position).toString());
        }
    }

}