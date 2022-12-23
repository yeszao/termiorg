package org.termi.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.termi.common.dto.MenuDto;
import org.termi.common.exception.NotFoundException;
import org.termi.admin.service.MenuService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.TreeTable;
import org.termi.common.entity.Menu;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminMenuController")
public class MenuController extends BaseController {
    private final MenuService service;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.MENU);
        model.addAttribute("PAGE_NAME", "menu");
    }

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping(MENU_BASE_URL)
    public String fetch(Model model) {
        List<Menu> entities = service.findAll();
        List<MenuDto> categoryDtoList = MenuDto.fromEntities(entities);
        TreeTable treeTable = TreeTable.fromTree(MenuDto.treeing(categoryDtoList), MenuDto.class);

        model.addAttribute("title", "Menus");
        model.addAttribute("tree", treeTable);
        return "tree";
    }

    @GetMapping(MENU_ADD_URL)
    public String add(Model model) {
        Menu entity = new Menu();
        return setFormModel("Add Menu", entity, model);
    }

    @GetMapping(MENU_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        Menu entity = service.findById(id)
                .orElseThrow(NotFoundException::new);

        return setFormModel("Edit Menu", entity, model);
    }

    @PostMapping(MENU_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") Menu entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Menu", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.insert(entity);

        return success(MENU_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(MENU_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") Menu entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Menu", entity, model);
        }

        entity.setEditBy(10L);
        Menu oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(MENU_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(MENU_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(MENU_BASE_URL, Map.of(), "Deleted", attributes);
    }
}