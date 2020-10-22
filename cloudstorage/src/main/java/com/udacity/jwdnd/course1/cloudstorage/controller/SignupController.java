package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
    @Autowired
    private UserService service;

    @GetMapping("/signup")
    public String showSignup(Model model){
        model.addAttribute("users", new Users());
        return "signup";
    }
    @PostMapping("/signup")
    public String signup(@Validated Users users, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("errMsg", "Something wrong");
        }
        service.insert(users);
        model.addAttribute("successMsg", "Success");
        model.addAttribute("users", new Users());
        return "signup";
    }
}
