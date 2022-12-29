package org.termi.website.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.termi.common.service.SettingService;

import java.io.IOException;


@Controller
@Slf4j
public class IndexController extends BaseController {

    @Autowired
    SettingService settingService;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        model.addAttribute("title", "Termi - All is ready");
        model.addAttribute("keywords", "Official Website,Shopping mall");
        model.addAttribute("description", "Termi is an official website and shopping cart system base one Spring Boot framework");

        model.addAttribute("uploadBaseUrl", settingService.getUploadBaseUrl());
        renderLayout("/", model);

        return "layout";
    }

}