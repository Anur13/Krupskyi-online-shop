package com.andrew.service;

import com.andrew.dao.ProductDao;
import com.andrew.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartService {
    @Autowired
    private ProductDao productDao;
    private SecurityService securityService;

    public List<Product> addProductToCart(int id, String token) {
        Product product = productDao.findProductById(id);
        List<Product> cart = securityService.getCartByToken(token);
        cart.add(product);
        return cart;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
