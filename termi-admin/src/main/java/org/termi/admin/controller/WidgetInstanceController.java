package org.termi.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.termi.admin.dto.Response;
import org.termi.admin.service.WidgetInstanceService;
import org.termi.common.entity.WidgetInstance;

import javax.validation.Valid;

import static org.termi.common.constant.AdminEndpoints.WIDGET_INSTANCE_ADD_URL;

@Controller("AdminWidgetInstanceController")
@Slf4j
public class WidgetInstanceController extends BaseController {
    @Autowired
    private WidgetInstanceService service;

    @PostMapping(WIDGET_INSTANCE_ADD_URL)
    @ResponseBody
    public Response<WidgetInstance> saveAdding(@Valid @ModelAttribute("entity") WidgetInstance entity) {
        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.insert(entity);

        return Response.success();
    }

}