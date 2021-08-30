package com.andrew.dao.jdbc;

import com.andrew.dao.ProductDao;
import com.andrew.dao.jdbc.mapper.ProductRowMapper;
import com.andrew.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products;";
    private static final String ADD_PRODUCTS_QUERY = "INSERT INTO products (name, price, date, description)" +
            " VALUES (?,?,?,?)";
    private static final String FIND_PRODUCT_BY_NAME_OR_DESCRIPTION_QUERY = "SELECT * FROM products " +
            "WHERE name Like ? OR description Like ?;";

    private static final String FIND_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE id = ?;";

    private static final String DELETE_PRODUCT_BY_ID_QUERY = "DELETE FROM products WHERE id = ?;";

    public List getAllProducts() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_QUERY, PRODUCT_ROW_MAPPER);
    }

    @Override
    public void addProduct(String name, int price, String description) {
        LocalDateTime date = LocalDateTime.now();
        jdbcTemplate.update(ADD_PRODUCTS_QUERY, name, price, Timestamp.valueOf(date), description);
    }

    @Override
    public Product findProductById(int id) {
        return (Product) jdbcTemplate.queryForObject(FIND_PRODUCT_BY_ID_QUERY, PRODUCT_ROW_MAPPER, id);
    }

    @Override
    public List<Product> findProductsByNameOrDescription(String query) {
        String templateParameter = '%' + query + '%';
        return (List<Product>) jdbcTemplate.query(FIND_PRODUCT_BY_NAME_OR_DESCRIPTION_QUERY,
                PRODUCT_ROW_MAPPER, templateParameter,
                templateParameter);
    }


    @Override
    public void deleteProduct(String id) {
        jdbcTemplate.update(DELETE_PRODUCT_BY_ID_QUERY, Integer.parseInt(id));
    }
}
