package com.example.bulletinboardapp.controller;

import com.example.bulletinboardapp.model.Content;
import com.example.bulletinboardapp.repository.ContentRepository;
import com.example.bulletinboardapp.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

//@RequiredArgsConstructor
@Controller
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public List<Content> getContents() {
        return contentService.getContent();
    }


    @GetMapping("/")
    public String home(Model model, @ModelAttribute Content content) {
        return contentService.home(model, content);
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute Content content,
                      BindingResult result,
                      Model model) {
        return contentService.add(content, result, model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return contentService.delete(id);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        return contentService.edit(id, model);
    }

    @PostMapping("/update")
    public String update(@Validated @ModelAttribute Content content,
                      BindingResult result,
                      Model model) {
        return contentService.update(content, result, model);
    }
}
