package com.alisonadamus.astronix.controller;

import com.alisonadamus.astronix.model.Material;
import com.alisonadamus.astronix.service.MarkdownService;
import com.alisonadamus.astronix.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;
    private final MarkdownService markdownService;

    @GetMapping("/{id}")
    public String showMaterial(@PathVariable Long id, Model model) {
        Material material = materialService.getById(id);
        String htmlContent = markdownService.renderHtml(material.getContent());
        material.setContent(htmlContent);
        model.addAttribute("material", material);
        return "material-details";
    }
}