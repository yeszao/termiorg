package org.termi.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.termi.admin.dto.Response;
import org.termi.admin.service.WidgetInstanceService;
import org.termi.common.entity.Layout;
import org.termi.common.entity.Widget;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.LayoutRepository;
import org.termi.common.repository.WidgetRepository;

import javax.validation.Valid;

import static org.termi.common.constant.AdminEndpoints.WIDGET_INSTANCE_ADD_URL;

@Controller("AdminWidgetInstanceController")
@Slf4j
public class WidgetInstanceController extends BaseController {
    @Autowired
    private WidgetInstanceService service;

    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private LayoutRepository layoutRepository;


    @PostMapping(WIDGET_INSTANCE_ADD_URL)
    @ResponseBody
    public Response<WidgetInstance> save(@Valid @RequestBody WidgetInstance entity) {
        entity.setAddBy(0L);
        entity.setEditBy(0L);

        Widget widget = widgetRepository.findById(entity.getWidgetId()).orElseThrow(NotFoundException::new);
        entity.setWidget(widget);
        Layout layout = layoutRepository.findById(entity.getLayoutId()).orElseThrow(NotFoundException::new);
        entity.setLayout(layout);

        if (entity.getId() == 0) {
            service.insert(entity);
        } else {
            WidgetInstance oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
            service.update(oldEntity, entity);
        }

        return Response.success();
    }

}