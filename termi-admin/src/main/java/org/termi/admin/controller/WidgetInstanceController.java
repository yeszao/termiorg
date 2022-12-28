package org.termi.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.termi.admin.dto.Response;
import org.termi.admin.service.WidgetInstanceService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.entity.Layout;
import org.termi.common.entity.Widget;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.LayoutRepository;
import org.termi.common.repository.WidgetRepository;

import javax.validation.Valid;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@RestController("AdminWidgetInstanceController")
@Slf4j
public class WidgetInstanceController extends BaseController {
    @Autowired
    private WidgetInstanceService service;

    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private LayoutRepository layoutRepository;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.WIDGET_INSTANCE);
        model.addAttribute("PAGE_NAME", "widget-instance");
    }


    @PostMapping(WIDGET_INSTANCE_ADD_URL)
    public Response<Map<String, Long>> save(@Valid @RequestBody WidgetInstance entity) {
        entity.setAddBy(0L);
        entity.setEditBy(0L);

        Widget widget = widgetRepository.findById(entity.getWidgetId()).orElseThrow(NotFoundException::new);
        entity.setWidget(widget);
        Layout layout = layoutRepository.findById(entity.getLayoutId()).orElseThrow(NotFoundException::new);
        entity.setLayout(layout);

        if (entity.getId() == 0) {
            service.save(entity);
        } else {
            WidgetInstance oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
            service.update(oldEntity, entity);
        }

        return Response.success(Map.of("id", entity.getId()));
    }

    @PostMapping(WIDGET_INSTANCE_SORT_URL)
    public Response<Map<String, Long>> sort(@RequestParam Long id, @RequestParam Integer sort) {
        WidgetInstance instance = service.findById(id).orElseThrow(NotFoundException::new);
        instance.setEditBy(0);
        instance.setSort(sort);

        service.save(instance);
        return Response.success(Map.of("id", id));
    }

    @PostMapping(WIDGET_INSTANCE_NAME_URL)
    public Response<Map<String, Long>> name(@RequestParam Long id, @RequestParam String name) {
        WidgetInstance instance = service.findById(id).orElseThrow(NotFoundException::new);
        instance.setEditBy(0);
        instance.setName(name);

        service.save(instance);
        return Response.success(Map.of("id", id));
    }

    @DeleteMapping(WIDGET_INSTANCE_DELETE_URL)
    public Response<Map<String, Long>> delete(@RequestParam Long id) {
        service.delete(id);
        return Response.success(Map.of("id", id));
    }
}