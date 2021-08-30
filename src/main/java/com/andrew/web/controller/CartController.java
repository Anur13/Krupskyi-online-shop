package com.andrew.web.controller;

import com.andrew.entity.Product;
import com.andrew.service.CartService;
import com.andrew.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    @Autowired
    CartService cartService;
    SecurityService securityService;

    @GetMapping
    public String getCartPage(@CookieValue("token") String token, Model model) {
        List cart = securityService.getCartByToken(token);
        model.addAttribute("products", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addProductToCart(@RequestParam("id") int id, @CookieValue("token") String token, Model model) {
        List<Product> cart = cartService.addProductToCart(id, token);
        model.addAttribute("products", cart);
        return "redirect:/cart";
    }

    @DeleteMapping("/delete")
    public String deleteProductFromCart() {
        return "redirect:/cart";

    }
}
