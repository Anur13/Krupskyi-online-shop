package com.andrew.dao;

import com.andrew.entity.Product;

import java.util.LinkedList;
import java.util.Map;

public interface ProductDao {
    LinkedList<Product> getAllProducts();

    LinkedList<Product> addProduct(String name, int price, String description);

    LinkedList<Product> findProductsByNameOrDescription(String query);

    void deleteProduct(String id);
}
