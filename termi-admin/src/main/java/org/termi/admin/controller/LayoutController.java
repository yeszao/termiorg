package org.termi.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.termi.admin.dto.LayoutDto;
import org.termi.admin.query.SearchQuery;
import org.termi.admin.service.LayoutService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.HtmlForm;
import org.termi.common.dto.PageableTable;
import org.termi.common.dto.WidgetHtmlForm;
import org.termi.common.entity.Layout;
import org.termi.common.entity.Widget;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.LayoutRepository;
import org.termi.common.repository.WidgetRepository;
import org.termi.common.service.WidgetInstanceService;
import org.termi.common.util.JsonUtil;
import org.termi.common.widget.WidgetPosition;
import org.termi.common.widget.WidgetRender;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminLayoutController")
@Slf4j
public class LayoutController extends BaseController {
    public static final String GLOBAL_LAYOUT_ENDPOINT = "~global~";

    private final LayoutService service;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private LayoutRepository layoutRepository;

    @Autowired
    private org.termi.common.service.LayoutService layoutService;

    @Autowired
    private WidgetInstanceService widgetInstanceService;

    @Autowired
    public LayoutController(LayoutService service) {
        this.service = service;
    }

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.LAYOUT);
        model.addAttribute("PAGE_NAME", "layout");
    }

    @GetMapping(LAYOUT_BASE_URL)
    public String fetch(@PageableDefault(sort = {"createAt"}, direction = Sort.Direction.DESC) Pageable pageable,
                        SearchQuery query, Model model) {
        Page<Layout> page = service.getList(pageable, query);
        model.addAttribute("query", query);
        model.addAttribute("pageable", PageableTable.fromPage(page, LayoutDto.class));
        model.addAttribute("title", "Layouts");

        return "list";
    }

    @GetMapping(LAYOUT_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        // widget layout instances
        Layout layout = service.findById(id).orElseThrow(NotFoundException::new);

        List<WidgetInstance> instances = widgetInstanceService.getInstances(List.of(GLOBAL_LAYOUT_ENDPOINT));
        Set<Long> globalLayoutInstanceIds = instances.stream().map(WidgetInstance::getId).collect(Collectors.toSet());
        if (!GLOBAL_LAYOUT_ENDPOINT.equals(layout.getEndpoint())) {
            instances.addAll(widgetInstanceService.getInstances(List.of(layout.getEndpoint())));
        }

        boolean isGlobalLayoutPage = GLOBAL_LAYOUT_ENDPOINT.equals(layout.getEndpoint());
        Map<WidgetPosition, List<Object>> layoutHtmlForms = layoutService.group(
                instances,
                ArrayList::new,
                (List<Object> s, WidgetRender renderer, WidgetInstance widgetInstance)
                        -> {
                    Object configObject = JsonUtil.parse(widgetInstance.getConfiguration(), renderer.getConfigurationClass());
                    HtmlForm htmlForm = HtmlForm.of(configObject);

                    boolean isGlobalLayoutWidgetInstance = globalLayoutInstanceIds.contains(widgetInstance.getId());
                    boolean allowSaving = isGlobalLayoutPage || !isGlobalLayoutWidgetInstance;

                    s.add(new WidgetHtmlForm(widgetInstance.getWidget(), htmlForm, widgetInstance, isGlobalLayoutWidgetInstance, allowSaving));
                }
        );

        List<Widget> widgets = widgetRepository.findAll();
        List<WidgetHtmlForm> widgetHtmlForms = new ArrayList<>();
        for (Widget widget : widgets) {
            WidgetRender renderer = context.getBean(widget.getRendererClassName(), WidgetRender.class);
            Class<?> configClass = renderer.getConfigurationClass();
            HtmlForm htmlForm = new HtmlForm();
            try {
                Object configObject = null;
                if (!Objects.isNull(configClass)) {
                    configObject = configClass.getDeclaredConstructor().newInstance();
                    htmlForm = HtmlForm.of(configObject);
                }
                WidgetInstance initInstance = new WidgetInstance();
                initInstance.setConfiguration(JsonUtil.objectToString(configObject));
                initInstance.setSort(49);
                initInstance.setId(0);

                widgetHtmlForms.add(new WidgetHtmlForm(widget, htmlForm, initInstance, false, false));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                log.error("Cannot get widget configuration by it's class name. {}", e.toString());
            }
        }

        model.addAttribute("topHtmlForms", layoutHtmlForms.get(WidgetPosition.TOP));
        model.addAttribute("leftHtmlForms", layoutHtmlForms.get(WidgetPosition.LEFT));
        model.addAttribute("centerHtmlForms", layoutHtmlForms.get(WidgetPosition.CENTER));
        model.addAttribute("rightHtmlForms", layoutHtmlForms.get(WidgetPosition.RIGHT));
        model.addAttribute("bottomHtmlForms", layoutHtmlForms.get(WidgetPosition.BOTTOM));

        model.addAttribute("widgetHtmlForms", widgetHtmlForms);

        model.addAttribute("layout", layout);
        model.addAttribute("title", "Edit Layout " + layout.getEndpoint());
        return "layout";
    }

    @PostMapping(LAYOUT_TRIGGER_API)
    @ResponseBody
    public ResponseEntity<Map<String, Long>> trigger(@RequestParam Long id,
                                                     @RequestParam WidgetPosition position,
                                                     @RequestParam boolean enabled) {
        Layout layout = service.findById(id).orElseThrow(NotFoundException::new);
        switch (position) {
            case TOP -> layout.setTopEnabled(enabled);
            case LEFT -> layout.setLeftEnabled(enabled);
            case CENTER -> layout.setCenterEnabled(enabled);
            case RIGHT -> layout.setRightEnabled(enabled);
            case BOTTOM -> layout.setBottomEnabled(enabled);
        }

        service.save(layout);
        return ResponseEntity.ok(Map.of("id", id));
    }
}