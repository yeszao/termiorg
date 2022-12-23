package org.termi.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.termi.admin.dto.AttachmentDto;
import org.termi.common.exception.NotFoundException;
import org.termi.admin.query.SearchQuery;
import org.termi.admin.service.AttachmentService;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.dto.PageableTable;
import org.termi.common.entity.Attachment;

import javax.validation.Valid;
import java.util.Map;

import static org.termi.common.constant.AdminEndpoints.*;

@Controller("AdminAttachmentController")
public class AttachmentController extends BaseController {
    private final AttachmentService service;

    @ModelAttribute
    public void setPageVariables(Model model) {
        model.addAllAttributes(AdminEndpoints.ATTACHMENT);
        model.addAttribute("PAGE_NAME", "attachment");
    }

    @Autowired
    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @GetMapping(ATTACHMENT_BASE_URL)
    public String fetch(@PageableDefault(sort = {"createAt"}, direction = Sort.Direction.DESC) Pageable pageable,
                        SearchQuery query, Model model) {
        Page<Attachment> page = service.getList(pageable, query);
        model.addAttribute("query", query);
        model.addAttribute("pageable", PageableTable.fromPage(page, AttachmentDto.class));
        model.addAttribute("title", "Attachments");

        return "list";
    }

    @GetMapping(ATTACHMENT_EDIT_URL)
    public String edit(@RequestParam Long id, Model model) {
        Attachment entity = service.findById(id).orElseThrow(NotFoundException::new);
        return setFormModel("Edit Attachment", entity, model);
    }

    @PostMapping(ATTACHMENT_EDIT_URL)
    public String save(@Valid @ModelAttribute("entity") Attachment entity, RedirectAttributes attributes) {
        entity.setEditBy(10L);
        Attachment oldEntity = service.findById(entity.getId()).orElseThrow(NotFoundException::new);
        service.update(oldEntity, entity);
        return success(ATTACHMENT_EDIT_URL, Map.of("id", entity.getId()), "Saved", attributes);
    }

    @GetMapping(ATTACHMENT_DELETE_URL)
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        service.delete(id);
        return success(ATTACHMENT_BASE_URL, Map.of(), "Deleted", attributes);
    }
}