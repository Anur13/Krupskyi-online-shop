package com.andrew.dao.jdbc;

import com.andrew.dao.UserDao;
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

    private final Logger LOGGER = new MyLogger().getLogger();

    @Override
    public List<User> getAllUsers() {
        try (Connection connection = myPGSimpleDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users");) {
            LinkedList<User> userslist = new LinkedList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(username, password);
                userslist.add(user);
            }
            return userslist;

        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Cannot get users from db", exception);
        }
    }

    @Override
    public void addUser(String username, String password) {
        String statement = "INSERT INTO users (username, password) VALUES (?,?)";
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
}
