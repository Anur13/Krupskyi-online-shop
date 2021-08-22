package com.andrew.dao.jdbc;

import com.andrew.dao.ProductDao;
import com.andrew.dao.jdbc.mapper.RowMapper;
import com.andrew.entity.Product;
import com.andrew.util.MyLogger;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class JdbcProductDao implements ProductDao {
    private static final RowMapper ROW_MAPPER = new RowMapper();
    private final MyPGSimpleDataSource MY_PGSIMPLE_DATA_SOURCE;
    private final Logger LOGGER = new MyLogger().getLogger();

    public JdbcProductDao(MyPGSimpleDataSource myPGSimpleDataSource) {
        this.MY_PGSIMPLE_DATA_SOURCE = myPGSimpleDataSource;
    }

    public LinkedList<Product> getAllProducts() {
        try (Connection connection = MY_PGSIMPLE_DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products");) {
            LinkedList<Product> productsList = new LinkedList<>();

            while (resultSet.next()) {
                productsList.add(ROW_MAPPER.mapProductRow(resultSet));
            }
            return productsList;

        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Cannot get products from db", exception);
        }

    }

    @Override
    public void addProduct(String name, int price, String description) {
        String statement = "INSERT INTO products (name, price, date, description) VALUES (?,?,?,?)";
        try (Connection connection = MY_PGSIMPLE_DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ) {
            LocalDateTime date = LocalDateTime.now();

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(date));
            preparedStatement.setString(4, description);

            preparedStatement.executeUpdate();


        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Cannot add product to db", exception);
        }

    }


    @Override
    public LinkedList<Product> findProductsByNameOrDescription(String query) {
        String statement = "SELECT * FROM products WHERE name Like ? OR description Like ?";
        try (Connection connection =  MY_PGSIMPLE_DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);

        ) {
            preparedStatement.setString(1, query + '%');
            preparedStatement.setString(2, query + '%');
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                LinkedList<Product> productsList = new LinkedList<>();
                while (resultSet.next()) {
                    productsList.add(ROW_MAPPER.mapProductRow(resultSet));
                }
                return productsList;
            }
        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Cannot find products in db", exception);
        }
    }


    @Override
    public void deleteProduct(String id) {
        String statement = "DELETE FROM products WHERE id = ?;";
        try (Connection connection = MY_PGSIMPLE_DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            new MyLogger().getLogger().error(exception);
            throw new RuntimeException("Cannot delete product from db", exception);
        }
    }
}
