package org.termi.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.termi.admin.service.SettingService;
import org.termi.common.setting.Carousel;

import javax.validation.Valid;

import static org.termi.common.constant.AdminEndpoints.CAROUSEL;
import static org.termi.common.constant.AdminEndpoints.CAROUSEL_EDIT_URL;

@Controller("AdminCombinedSettingController")
@Slf4j
public class CombinedSettingController extends BaseController {
    @Autowired
    private SettingService service;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(CAROUSEL);
        model.addAttribute("PAGE_NAME", "carousel");
    }


    @GetMapping(CAROUSEL_EDIT_URL)
    public String edit(Model model) {
        Carousel entity = service.getJson("carousel", Carousel.class);
        return setFormModel("Edit Setting", entity, model);
    }

    @PostMapping(CAROUSEL_EDIT_URL)
    public String saveEditing(@Valid @ModelAttribute("entity") Carousel entity,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return setFormModel("Edit Setting", entity, model);
        }

        service.setJson("carousel", entity);

        return success(CAROUSEL_EDIT_URL,"Saved", attributes);
    }
}