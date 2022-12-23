package org.termi.admin.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.termi.admin.service.CategoryService;
import org.termi.admin.service.MenuService;
import org.termi.admin.service.ProductSpecificationService;
import org.termi.admin.service.StorageService;
import org.termi.common.dto.CategoryDto;
import org.termi.common.dto.MenuDto;
import org.termi.common.dto.SelectOption;
import org.termi.common.dto.TreeBaseDto;
import org.termi.common.entity.Attachment;
import org.termi.common.entity.Category;
import org.termi.common.entity.Menu;
import org.termi.common.entity.ProductSpecification;
import org.termi.common.enumeration.PageStatus;
import org.termi.common.enumeration.ProductStatus;
import org.termi.common.widget.component.Icon;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.termi.common.constant.AdminEndpoints.*;

@RestController("AdminApiController")
public class ApiController {
    @Autowired
    private CategoryService adminCategoryService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ProductSpecificationService productSpecificationService;

    @Autowired
    private StorageService storageService;

    @GetMapping(PAGE_STATUS_API)
    public List<SelectOption> pageStatus() {
        return SelectOption.listOf(List.of(PageStatus.values()), PageStatus::name, PageStatus::getText);
    }

    @GetMapping(PRODUCT_STATUS_API)
    public List<SelectOption> productStatus() {
        return SelectOption.listOf(List.of(ProductStatus.values()), ProductStatus::name, ProductStatus::name);
    }

    @GetMapping(ICON_ALL_API)
    public List<SelectOption> icons() {
        return SelectOption.listOf(List.of(Icon.values()), Icon::name, x -> x.getHtml() + StringUtils.SPACE + x.getCode());
    }

    @GetMapping(PRODUCT_SPECIFICATION_ALL_API)
    public List<SelectOption> specifications() {
        List<ProductSpecification> specifications = productSpecificationService.findAll();
        return SelectOption.listOf(specifications, ProductSpecification::getId, ProductSpecification::getName);
    }

    @GetMapping(CATEGORY_ALL_API)
    public List<SelectOption> categories(@RequestParam(defaultValue = "0") Long id) {
        List<Category> categories = adminCategoryService.findAll();
        List<? extends TreeBaseDto> dtoList = CategoryDto.treeing(CategoryDto.fromEntities(categories));

        Set<Long> disabledIds = new HashSet<>();
        if (!id.equals(0L)) {
            disabledIds = CategoryDto.getChildIdsOf(Collections.singleton(id), dtoList);
        }

        List<SelectOption> options = Lists.newArrayList(SelectOption.of(0L, "---"));
        options.addAll(CategoryDto.toSelectOptions(dtoList, disabledIds));

        return options;
    }

    /**
     * @param id parentId
     */
    @GetMapping(MENU_ALL_API)
    public List<SelectOption> menus(@RequestParam(defaultValue = "0") Long id) {
        List<Menu> menus = menuService.findAll();
        List<? extends TreeBaseDto> dtoList = CategoryDto.treeing(MenuDto.fromEntities(menus));

        Set<Long> disabledIds = new HashSet<>();
        if (!id.equals(0L)) {
            disabledIds = CategoryDto.getChildIdsOf(Collections.singleton(id), dtoList);
        }

        List<SelectOption> options = Lists.newArrayList(SelectOption.of(0L, "---"));
        options.addAll(CategoryDto.toSelectOptions(dtoList, disabledIds));

        return options;
    }

    @PostMapping(ATTACHMENT_UPLOAD_API)
    public Attachment upload(@RequestParam("file") MultipartFile file) throws IOException {
        return storageService.upload(file);
    }

}