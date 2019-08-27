package com.kevcoucflab.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getRoot(Principal p, Model m) {
        m.addAttribute("user", p);
        return "root";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "registration";
    }
}
