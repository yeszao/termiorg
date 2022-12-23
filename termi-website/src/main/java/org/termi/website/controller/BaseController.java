package org.termi.website.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.termi.common.configuration.UploadConfig;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.repository.LayoutRepository;
import org.termi.common.service.LayoutService;
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
    private ApplicationContext context;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LayoutRepository layoutRepository;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private WidgetInstanceService widgetInstanceService;

    @ModelAttribute
    public void setGlobalVariables(Model model) {
        model.addAttribute("UPLOAD_BASE_URL", uploadConfig.getBaseUrl());
    }

    public void renderLayout(String endpointName, Model model) {
        List<WidgetInstance> instances = widgetInstanceService
                .getInstances(List.of(endpointName, "~global~"));

        Map<WidgetPosition, StringBuilder> rendered = layoutService.group(
                instances,
                StringBuilder::new,
                (StringBuilder s, WidgetRender renderer, WidgetInstance widgetInstance)
                        -> {
                    s.append(renderer.render(widgetInstance.getConfiguration(), request, model));
                }
        );

        // output to html
        for (WidgetPosition p : WidgetPosition.values()) {
            model.addAttribute(p.name(), rendered.get(p).toString());
        }
    }

}