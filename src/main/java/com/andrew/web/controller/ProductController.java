package com.andrew.web.controller;

import com.andrew.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@AllArgsConstructor
@Controller
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @GetMapping("/add")
    public String getAddProductPage()  {
        return "addProductPage";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam int price, @RequestParam String description) {
        productService.addProduct(name, price, description);
        return "redirect:/products";
    }

    @GetMapping("/search")
    @ResponseBody
    public String findProductBy(@RequestParam String query, Model model) {
        model.addAttribute("products", productService.findProductsByNameOrDescription(query));
        return "index";
    }



}
