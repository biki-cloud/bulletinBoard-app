package com.example.bulletinboardapp.controller;

import com.example.bulletinboardapp.model.Content;
import com.example.bulletinboardapp.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@RequiredArgsConstructor
@Controller
public class ContentController {

    private final ContentRepository repository;

    @GetMapping("/")
    public String home(Model model, @ModelAttribute Content content) {
        System.out.println("homeメソッドが呼ばれました");
        model.addAttribute("greeingMessage", "Welcom to Bulletin board app.");
        model.addAttribute("contents", repository.findAll());
        return "home";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute Content content,
                      BindingResult result,
                      Model model) {
        System.out.println("addメソッドが呼ばれました");
        model.addAttribute("contents", repository.findAll());
        if (result.hasErrors()) {
            System.out.println("バインドエラーが起きました");
            System.out.println(result.getFieldErrors());
            model.addAttribute("greeingMessage", "Welcom to Bulletin board app.");
            return "home";
        }
        Date nowTime = new Date();
        content.setCreatedDate(nowTime);
        repository.save(content);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("deleteメソッドが呼ばれました");
        repository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        System.out.println("editメソッドが呼ばれました");
        model.addAttribute("content", repository.findById(id));
        return "contentEdit";
    }

    @PostMapping("/update")
    public String update(@Validated @ModelAttribute Content content,
                      BindingResult result,
                      Model model) {
        System.out.println("updateメソッドが呼ばれました");
        model.addAttribute("contents", repository.findAll());
        if (result.hasErrors()) {
            return "contentEdit";
        }
        System.out.println(content.getCreatedDate());
        Date nowTime = new Date();
        content.setUpdatedDate(nowTime);
        repository.save(content);
        return "redirect:/";
    }
}
