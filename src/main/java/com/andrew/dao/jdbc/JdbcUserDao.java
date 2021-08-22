package com.andrew.dao.jdbc;

import com.andrew.dao.UserDao;
import com.andrew.dao.jdbc.mapper.RowMapper;
import com.andrew.entity.User;
import com.andrew.util.MyLogger;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class JdbcUserDao implements UserDao {

    private MyPGSimpleDataSource myPGSimpleDataSource;

    public JdbcUserDao(MyPGSimpleDataSource myPGSimpleDataSource) {
        this.myPGSimpleDataSource = myPGSimpleDataSource;
    }

    private static final RowMapper ROW_MAPPER = new RowMapper();
    private final Logger LOGGER = new MyLogger().getLogger();


    @Override
    public void addUser(String username, String password) {
        String statement = "INSERT INTO users (username, password) VALUES (?,?);";
        try (Connection connection = myPGSimpleDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Cannot add user to db", exception);
        }
    }

    public User getUser(String username) {
        String statement = "SELECT * FROM users WHERE username = ?;";
        try (Connection connection = myPGSimpleDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                User user = null;
                while (resultSet.next()) {
                    user = ROW_MAPPER.mapUserRow(resultSet);
                }
                return user;
            }
        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Cannot find user in db", exception);
        }

    }
}
