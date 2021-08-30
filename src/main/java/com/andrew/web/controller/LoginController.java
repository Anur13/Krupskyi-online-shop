package com.andrew.web.controller;

import com.andrew.service.SecurityService;
import com.andrew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    SecurityService securityService;

    @GetMapping
    public String getLoginPage(){
        return "loginPage";
    }

    @PostMapping
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletResponse response){
        String token = securityService.login(username, password);
        if (token != null) {
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
        }
        return "redirect:/products";
    }


}
