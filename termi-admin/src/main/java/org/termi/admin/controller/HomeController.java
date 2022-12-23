package org.termi.admin.controller;

import org.termi.common.constant.AdminEndpoints;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.termi.common.constant.AdminEndpoints.HOME_URL;

@Controller("AdminHomeController")
public class HomeController extends BaseController {
    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.HOME);
        model.addAttribute("PAGE_NAME", "home");
    }

    @GetMapping(HOME_URL)
    public String home(Model model) {
        model.addAttribute("title", "Dashboard");
        return "index";
    }
}