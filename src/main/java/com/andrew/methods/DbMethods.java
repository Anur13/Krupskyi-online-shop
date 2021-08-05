package com.andrew.methods;

import com.andrew.entities.Product;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class DbMethods {

    static Statement statement;
    static ResultSet resultSet;


    private Date date;
    private String id;
    private Map<String, LinkedList<Product>> productsMap = new HashMap<>();
    private LinkedList<Product> productsList = new LinkedList<>();
    private Connection connection;

    public DbMethods(Connection connection) {
        this.connection = connection;
    }

    public Map<String, LinkedList<Product>> getAllProductsMap(String tableName) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM " + tableName);


        date = java.sql.Date.valueOf(java.time.LocalDate.now());
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            id = resultSet.getString("Id");
            date = resultSet.getDate("Date");
            Product product = new Product(name, price, date, id);
            productsList.add(product);
        }
        productsMap.put("products", productsList);
        return productsMap;
    }

    public Map<String, LinkedList<Product>> addProduct(String tableName, String name, int price)
            throws SQLException {
        String statement = "INSERT INTO " + tableName + " (id, name, price, date) VALUES (?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        id = UUID.randomUUID().toString();
        date = java.sql.Date.valueOf(java.time.LocalDate.now());

        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, price);
        preparedStatement.setDate(4, date);


        Product product = new Product(name, price, date, id);
        System.out.println();
//        productsList = getAllProductsMap(tableName);
        productsList.add(product);
        productsMap.put("products", productsList);


        preparedStatement.executeUpdate();
        return productsMap;
    }
}
