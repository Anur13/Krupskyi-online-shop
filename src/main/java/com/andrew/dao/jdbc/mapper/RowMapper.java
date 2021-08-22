package com.andrew.dao.jdbc.mapper;

import com.andrew.entity.Product;
import com.andrew.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RowMapper {
    public Product mapProductRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        LocalDateTime localDateTime = resultSet.getTimestamp("Date").toLocalDateTime();
        String description = resultSet.getString("description");

        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .date(localDateTime.toLocalDate())
                .description(description)
                .build();
    }
    public User mapUserRow(ResultSet resultSet) throws SQLException{
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
