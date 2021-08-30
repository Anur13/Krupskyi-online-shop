package com.andrew.web.controller;

import com.andrew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegistationController {
    @Autowired
    UserService userService;

    @GetMapping
    public String getRegistrationPage() {
        return "registrationPage";
    }

    @PostMapping
    public String registerUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        userService.addUser(username,password);

        String cookieId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("token", cookieId);

        response.addCookie(cookie);
        return "redirect:/products";
    }
}
