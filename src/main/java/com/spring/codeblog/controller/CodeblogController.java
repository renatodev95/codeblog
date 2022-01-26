package com.spring.codeblog.controller;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.service.CodeblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class CodeblogController {

    @Autowired
    private CodeblogService codeblogService;

    @GetMapping("/posts")
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = codeblogService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @GetMapping("/posts/{id}")
    public ModelAndView getPostDetails(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = codeblogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @GetMapping("/newpost")
    public String getPostForm() {
        return "postForm";
    }

    @PostMapping("/newpost")
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:/newpost";
        }
        post.setData(LocalDate.now());
        codeblogService.save(post);
        return "redirect:/posts";
    }
}
