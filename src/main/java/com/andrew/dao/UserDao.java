package com.andrew.dao;

import com.andrew.entity.User;

import java.util.List;

public interface UserDao {

   void addUser(String username, String password);
   User getUserByName(String username);
}
