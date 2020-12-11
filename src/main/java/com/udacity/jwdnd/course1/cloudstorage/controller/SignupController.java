package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
/**
 * @Author Mohammad Rajaul Islam *
 * @Since v.1.0
 */
@Controller
public class SignupController {
    @Autowired
    private UserService service;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/signup")
    public String showSignup(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            /* The user is logged in :) */
            return "redirect:/";
        }
        model.addAttribute("users", new User());
        return "signup";
    }

    /**
     *
     * @param users
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/signup")
    public String signup(@Validated User users, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("errMsg", "Something wrong");
        }else if(service.findByUserName(users.getUsername()) != null){
            model.addAttribute("alreadyExist");
        }
        service.insert(users);
        model.addAttribute("successMsg", "Success");
        model.addAttribute("users", new User());
        return "signup";
    }
}
