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
import org.termi.admin.dto.ProductDto;
import org.termi.admin.query.SearchQuery;
import org.termi.admin.service.AttachmentService;
import org.termi.admin.service.CategoryService;
import org.termi.admin.service.ProductService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.PageableTable;
import org.termi.common.entity.Product;
import org.termi.common.exception.NotFoundException;

import javax.validation.Valid;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminProductController")
@Slf4j
public class ProductController extends BaseController {
    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttachmentService attachmentService;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.PRODUCT);
        model.addAttribute("PAGE_NAME", "product");
    }

    @GetMapping(PRODUCT_BASE_URL)
    public String fetch(@PageableDefault(sort = {"createAt"}, direction = Sort.Direction.DESC) Pageable pageable,
                        SearchQuery query,
                        Model model) {
        Page<Product> page = service.getList(pageable, query);
        PageableTable<ProductDto> pageableTable = PageableTable.fromPage(page, ProductDto.class);

        model.addAttribute("query", query);
        model.addAttribute("pageable", pageableTable);
        model.addAttribute("title", "Products");

        return "list";
    }

    @GetMapping(PRODUCT_ADD_URL)
    public String add(Model model) {
        return setFormModel("Add Product", new Product(), model);
    }

    @GetMapping(PRODUCT_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        Product entity = service.findById(id).orElseThrow(NotFoundException::new);
        return setFormModel("Edit Product", entity, model);
    }

    private void checkCategory(Product entity, BindingResult bindingResult) {
        categoryService
                .findById(entity.getCategoryId())
                .ifPresentOrElse(entity::setCategory,
                        () -> bindingResult.rejectValue("categoryId", "categoryError", "Category do not exist"));
    }

    @PostMapping(PRODUCT_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") Product entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        checkCategory(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Product", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.save(entity);

        return success(PRODUCT_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(PRODUCT_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") Product entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        checkCategory(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Product", entity, model);
        }

        entity.setEditBy(10L);
        Product oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(PRODUCT_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(PRODUCT_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(PRODUCT_BASE_URL, Map.of(), "Deleted", attributes);
    }
}