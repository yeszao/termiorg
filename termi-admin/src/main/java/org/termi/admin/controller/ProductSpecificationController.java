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
import org.termi.admin.dto.ProductSpecificationDto;
import org.termi.common.exception.NotFoundException;
import org.termi.admin.query.SearchQuery;
import org.termi.admin.service.ProductSpecificationService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.PageableTable;
import org.termi.common.entity.ProductSpecification;

import javax.validation.Valid;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminProductSpecificationController")
public class ProductSpecificationController extends BaseController {
    private final ProductSpecificationService service;

    @Autowired
    public ProductSpecificationController(ProductSpecificationService service) {
        this.service = service;
    }

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.PRODUCT_SPECIFICATION);
        model.addAttribute("PAGE_NAME", "product-specification");
    }

    @GetMapping(PRODUCT_SPECIFICATION_BASE_URL)
    public String fetch(@PageableDefault(sort = {"createAt"}, direction = Sort.Direction.DESC) Pageable pageable,
                        SearchQuery query, Model model) {
        Page<ProductSpecification> page = service.getList(pageable, query);
        model.addAttribute("query", query);
        model.addAttribute("pageable", PageableTable.fromPage(page, ProductSpecificationDto.class));
        model.addAttribute("title", "Product Specifications");

        return "list";
    }

    @GetMapping(PRODUCT_SPECIFICATION_ADD_URL)
    public String add(Model model) {
        ProductSpecification entity = new ProductSpecification();
        return setFormModel("Add Product Specification", entity, model);
    }

    @GetMapping(PRODUCT_SPECIFICATION_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        ProductSpecification entity = service.findById(id)
                .orElseThrow(NotFoundException::new);

        return setFormModel("Edit Product Specification", entity, model);
    }

    @PostMapping(PRODUCT_SPECIFICATION_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") ProductSpecification entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Product Specification", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.insert(entity);

        return success(PRODUCT_SPECIFICATION_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(PRODUCT_SPECIFICATION_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") ProductSpecification entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Product Specification", entity, model);
        }

        entity.setEditBy(10L);
        ProductSpecification oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(PRODUCT_SPECIFICATION_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(AdminEndpoints.PRODUCT_SPECIFICATION_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(PRODUCT_SPECIFICATION_BASE_URL, Map.of(), "Deleted", attributes);
    }
}