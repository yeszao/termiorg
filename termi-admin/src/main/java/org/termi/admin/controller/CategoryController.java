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
import org.termi.common.dto.CategoryDto;
import org.termi.common.exception.NotFoundException;
import org.termi.admin.service.CategoryService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.TreeTable;
import org.termi.common.entity.Category;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminCategoryController")
public class CategoryController extends BaseController {
    private final CategoryService service;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.CATEGORY);
        model.addAttribute("PAGE_NAME", "category");
    }

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping(CATEGORY_BASE_URL)
    public String fetch(Model model) {
        List<Category> categories = service.findAll();
        List<CategoryDto> categoryDtoList = CategoryDto.fromEntities(categories);
        TreeTable treeTable = TreeTable.fromTree(CategoryDto.treeing(categoryDtoList), CategoryDto.class);

        model.addAttribute("title", "Edit Category");
        model.addAttribute("tree", treeTable);
        return "tree";
    }

    @GetMapping(CATEGORY_ADD_URL)
    public String add(Model model) {
        return setFormModel("Add Category", new Category(), model);
    }

    @GetMapping(CATEGORY_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        Category entity = service.findById(id)
                .orElseThrow(NotFoundException::new);

        return setFormModel("Edit Category", entity, model);
    }

    @PostMapping(CATEGORY_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") Category entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Category", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.save(entity);

        return success(CATEGORY_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(CATEGORY_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") Category entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Category", entity, model);
        }

        entity.setEditBy(10L);
        Category oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(CATEGORY_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(CATEGORY_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(CATEGORY_BASE_URL, Map.of(), "Deleted", attributes);
    }
}