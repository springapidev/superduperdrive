package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @Author Mohammad Rajaul Islam *
 * @Since v.1.0
 */
@Controller
public class LoginController {
    /**
     *
     * @return
     */
    @GetMapping("/login")
    public String showHome(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            /* The user is logged in :) */
            return "redirect:/";
        }
        return "login";
    }
}
