package com.andrew.dao;

import com.andrew.entity.Product;

import java.util.LinkedList;

public interface ProductDao {
    LinkedList<Product> getAllProducts();

    void addProduct(String name, int price, String description);

    LinkedList<Product> findProductsByNameOrDescription(String query);

    void deleteProduct(String id);
}
