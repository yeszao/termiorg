package org.termi.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.termi.common.configuration.TermiConfig;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.HtmlForm;
import org.termi.common.service.SettingService;
import org.termi.common.util.MapUtil;

import java.util.Map;

@ControllerAdvice
public class BaseController {
    @Autowired
    private SettingService settingService;

    @Autowired
    private TermiConfig termiConfig;

    @ModelAttribute
    public void setGlobalVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.ALL);
        model.addAttribute("setting", settingService.getAll());
        model.addAttribute("termiConfig", termiConfig);
    }

    protected <T> String setFormModel(String title, T entity, Model model) {
        model.addAttribute("entity", entity);
        model.addAttribute("form", HtmlForm.of(entity));
        model.addAttribute("title", title);
        return "add";
    }

    protected String success(String redirectUrl,
                             Map<String, Object> params,
                             String message,
                             RedirectAttributes attributes) {
        attributes.addFlashAttribute("success", message);

        if (params.isEmpty()) {
            return String.format("redirect:%s", redirectUrl);
        }
        return String.format("redirect:%s?%s", redirectUrl, MapUtil.toQueryString(params));
    }
}