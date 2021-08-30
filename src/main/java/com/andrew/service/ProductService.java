package com.andrew.service;

import com.andrew.dao.ProductDao;
import com.andrew.entity.Product;

import java.util.LinkedList;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public List<Product> findProductsByNameOrDescription(String query) {
        return productDao.findProductsByNameOrDescription(query);
    }

    public void deleteProduct(String id) {
        productDao.deleteProduct(id);
    }

    public void addProduct(String name, int price, String description) {
        productDao.addProduct(name, price, description);
    }

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }
}
