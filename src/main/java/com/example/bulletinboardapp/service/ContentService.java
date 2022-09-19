package com.example.bulletinboardapp.service;

import com.example.bulletinboardapp.model.Content;
import com.example.bulletinboardapp.repository.ContentRepository;
import com.example.bulletinboardapp.util.ContentUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Service
public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    public List<Content> getContent() {
        return List.of(
                new Content(
                        1L,
                        "biki",
                        "test comment",
                        new Date()
                )
        );
    }

    public String home(Model model, @ModelAttribute Content content) {
        System.out.println("homeメソッドが呼ばれました");
        model.addAttribute("greeingMessage", ContentUtil.getMessage());
        model.addAttribute("contents", repository.findAll());
        return "home";
    }

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

    public String delete(@PathVariable Long id) {
        System.out.println("deleteメソッドが呼ばれました");
        repository.deleteById(id);
        return "redirect:/";
    }

    public String edit(@PathVariable Long id, Model model) {
        System.out.println("editメソッドが呼ばれました");
        model.addAttribute("content", repository.findById(id));
        return "contentEdit";
    }

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
        content.setCreatedDate(nowTime);
        repository.save(content);
        return "redirect:/";
    }

}
