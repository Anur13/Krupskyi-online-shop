package com.andrew.dao;

import com.andrew.entity.Product;

import java.util.LinkedList;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();

    void addProduct(String name, int price, String description);

    List<Product> findProductsByNameOrDescription(String query);

    void deleteProduct(String id);

    Product findProductById(int id);
}
