package com.andrew.dao.jdbc.mapper;

import com.andrew.entity.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper {
    public User mapUserRow(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
       return mapUserRow(resultSet);
    }
}
