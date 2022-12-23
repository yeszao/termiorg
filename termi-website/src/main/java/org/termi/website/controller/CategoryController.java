package org.termi.website.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.termi.common.dto.Breadcrumb;
import org.termi.common.entity.Category;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.CategoryRepository;
import org.termi.common.widget.component.IconText;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Controller
@Slf4j
public class CategoryController extends BaseController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping({"/categories", "/category/{slug}"})
    public String index(Model model, @PathVariable(required = false) String slug) throws IOException {
        if (!Objects.isNull(slug)) {
            Category category = categoryRepository.findFirstBySlug(slug)
                    .orElseThrow(NotFoundException::new);
            model.addAttribute("categoryId", category.getId());
        } else {
            model.addAttribute("categoryId", 0L);
        }

        model.addAttribute("title", "All Products - Termi");
        model.addAttribute("keywords", "");
        model.addAttribute("description", "");


        List<IconText> items = List.of(
                new IconText("bi-home", "Home", "/"),
                new IconText("", "Products", "/products")
        );

        Breadcrumb breadcrumb = new Breadcrumb("Products", items);
        model.addAttribute("breadcrumb", breadcrumb);

        renderLayout("/category/{slug}", model);

        return "layout";
    }

}