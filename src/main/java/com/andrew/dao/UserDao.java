package com.andrew.dao;

import com.andrew.entity.User;

import java.util.List;

public interface UserDao {

   List<User> getAllUsers();

   void addUser(String username, String password);
}
