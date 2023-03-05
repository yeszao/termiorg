package org.termi.admin.controller;

import lombok.extern.slf4j.Slf4j;
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
import org.termi.admin.dto.SettingDto;
import org.termi.admin.query.SearchQuery;

import org.termi.admin.service.SettingService;
import org.termi.common.dto.PageableTable;
import org.termi.common.entity.Setting;
import org.termi.common.exception.NotFoundException;

import javax.validation.Valid;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminSettingController")
@Slf4j
public class SettingController extends BaseController {
    @Autowired
    private SettingService service;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(SETTING);
        model.addAttribute("PAGE_NAME", "setting");
    }

    @GetMapping(SETTING_BASE_URL)
    public String fetch(@PageableDefault(sort = {"createAt"}, direction = Sort.Direction.DESC) Pageable pageable,
                        SearchQuery query,
                        Model model) {
        Page<Setting> page = service.getList(pageable, query);
        PageableTable<SettingDto> pageableTable = PageableTable.fromPage(page, SettingDto.class);

        model.addAttribute("query", query);
        model.addAttribute("pageable", pageableTable);
        model.addAttribute("title", "Settings");

        return "list";
    }

    @GetMapping(SETTING_ADD_URL)
    public String add(Model model) {
        return setFormModel("Add Setting", new Setting(), model);
    }

    @GetMapping(SETTING_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        Setting entity = service.findById(id).orElseThrow(NotFoundException::new);
        return setFormModel("Edit Setting", entity, model);
    }

    @PostMapping(SETTING_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") Setting entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Setting", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.save(entity);

        return success(SETTING_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(SETTING_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") Setting entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Setting", entity, model);
        }

        entity.setEditBy(10L);
        Setting oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(SETTING_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(SETTING_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(SETTING_BASE_URL, Map.of(), "Deleted", attributes);
    }
}