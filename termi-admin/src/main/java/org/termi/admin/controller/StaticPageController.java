package org.termi.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.termi.admin.dto.StaticPageDto;
import org.termi.common.exception.NotFoundException;
import org.termi.admin.query.SearchQuery;
import org.termi.admin.service.StaticPageService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.PageableTable;
import org.termi.common.entity.StaticPage;

import javax.validation.Valid;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminStaticPageController")
public class StaticPageController extends BaseController {
    private final StaticPageService service;

    @Autowired
    public StaticPageController(StaticPageService service) {
        this.service = service;
    }

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.PAGE);
        model.addAttribute("PAGE_NAME", "page");
    }

    @GetMapping(PAGE_BASE_URL)
    public String fetch(@PageableDefault(sort = {"createAt"}, direction = Sort.Direction.DESC) Pageable pageable,
                        SearchQuery query, Model model) {
        Page<StaticPage> page = service.getList(pageable, query);
        model.addAttribute("query", query);
        model.addAttribute("pageable", PageableTable.fromPage(page, StaticPageDto.class));
        model.addAttribute("title", "Static Pages");

        return "list";
    }

    @GetMapping(PAGE_ADD_URL)
    public String add(Model model) {
        StaticPage entity = new StaticPage();
        return setFormModel("Add Static Pages", entity, model);
    }

    @GetMapping(PAGE_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        StaticPage entity = service.findById(id)
                .orElseThrow(NotFoundException::new);
        return setFormModel("Edit Static Pages", entity, model);
    }

    @PostMapping(PAGE_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") StaticPage entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Static Page", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.insert(entity);

        return success(PAGE_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(PAGE_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") StaticPage entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Static Page", entity, model);
        }

        entity.setEditBy(10L);
        StaticPage oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(PAGE_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(PAGE_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(PAGE_BASE_URL, Map.of(), "Deleted", attributes);
    }
}