package com.election.project.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.election.project.entity.User;
import com.election.project.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, User user) {

        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, User user) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerSava(@ModelAttribute("user") User user, Model model) {
        User temp = userService.findByUsername(user.getUsername());
        if (temp != null) {
            model.addAttribute("Userexist", user);
            return "register";
        }
        userService.save(user);
        return "redirect:/register?success";
    }
}
