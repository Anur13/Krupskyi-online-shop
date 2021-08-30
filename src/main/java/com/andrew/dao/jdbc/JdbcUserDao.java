package com.andrew.dao.jdbc;

import com.andrew.dao.UserDao;
import com.andrew.dao.jdbc.mapper.UserRowMapper;
import com.andrew.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private JdbcTemplate jdbcTemplate;

    private static final String ADD_USER_QUERY = "INSERT INTO users (username, password) VALUES (?,?);";
    private static final String GET_USER_BY_NAME_QUERY = "SELECT * FROM users WHERE username = ?;";
//    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM "
    @Override
    public void addUser(String username, String password) {
        jdbcTemplate.update(ADD_USER_QUERY, username, password);
    }

    public User getUserByName(String username) {
        return (User) jdbcTemplate.queryForObject(GET_USER_BY_NAME_QUERY, USER_ROW_MAPPER, username);
    }

//    public List<User> getAllUsers(){
//
//    }
//    private boolean doesUserExist(String username){
//
//        return false;
//    }

}
