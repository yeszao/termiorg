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
import org.termi.admin.dto.ProductSpecificationValueDto;
import org.termi.common.exception.NotFoundException;
import org.termi.admin.query.ChildQuery;
import org.termi.admin.service.ProductSpecificationService;
import org.termi.admin.service.ProductSpecificationValueService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.PageableTable;
import org.termi.common.entity.ProductSpecification;
import org.termi.common.entity.ProductSpecificationValue;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminProductSpecificationValueController")
public class ProductSpecificationValueController extends BaseController {
    private final ProductSpecificationValueService service;
    private final ProductSpecificationService specificationService;

    @Autowired
    public ProductSpecificationValueController(ProductSpecificationValueService service,
                                               ProductSpecificationService specificationService) {
        this.service = service;
        this.specificationService = specificationService;
    }

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.PRODUCT_SPECIFICATION_VALUE);
        model.addAttribute("PAGE_NAME", "product-specification-value");
    }

    @GetMapping(PRODUCT_SPECIFICATION_VALUE_BASE_URL)
    public String fetch(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        ChildQuery query, Model model) {
        Optional<ProductSpecification> specification = specificationService.findById(query.parentId());

        Page<ProductSpecificationValue> page = service.getList(pageable, query);
        model.addAttribute("query", query);
        model.addAttribute("pageable", PageableTable.fromPage(page, ProductSpecificationValueDto.class));
        String pageTitle = "%s values".formatted(specification.isPresent() ? specification.get().getName() : "All");
        model.addAttribute("title", pageTitle);
        model.addAttribute("parentId", query.parentId());

        return "list";
    }

    @GetMapping(PRODUCT_SPECIFICATION_VALUE_ADD_URL)
    public String add(@RequestParam(defaultValue = "0") Long parentId, Model model) {
        ProductSpecificationValue entity = new ProductSpecificationValue();
        entity.setProductSpecificationId(parentId);

        model.addAttribute("parentId", parentId);
        return setFormModel("Add Product Specification Value", entity, model);
    }

    @GetMapping(PRODUCT_SPECIFICATION_VALUE_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        ProductSpecificationValue entity = service.findById(id).orElseThrow(NotFoundException::new);

        entity.setProductSpecificationId(entity.getProductSpecification().getId());

        model.addAttribute("parentId", entity.getProductSpecification().getId());
        return setFormModel("Add Product Specification Value", entity, model);
    }


    @PostMapping(PRODUCT_SPECIFICATION_VALUE_ADD_URL)
    public String saveAdding(@Valid @ModelAttribute("entity") ProductSpecificationValue entity,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Add Product Specification Value", entity, model);
        }

        entity.setAddBy(0L);
        entity.setEditBy(0L);
        service.insert(entity);

        return success(PRODUCT_SPECIFICATION_VALUE_EDIT_URL, Map.of("id", entity.getId()), "Added", attributes);
    }

    @PostMapping(PRODUCT_SPECIFICATION_VALUE_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") ProductSpecificationValue entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Product Specification Value", entity, model);
        }

        entity.setEditBy(10L);
        ProductSpecificationValue oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);

        return success(PRODUCT_SPECIFICATION_VALUE_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(PRODUCT_SPECIFICATION_VALUE_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(PRODUCT_SPECIFICATION_VALUE_BASE_URL, Map.of(), "Deleted", attributes);
    }
}