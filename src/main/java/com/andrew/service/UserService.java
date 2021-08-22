package com.andrew.service;

import com.andrew.dao.UserDao;
import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.entity.User;

public class UserService {
    UserDao userDao;
    SecurityService securityService;

    public void addUser(String username, String password) {
        userDao.addUser(username, securityService.encryptPassword(password));
    }

    public User getUser(String username) {
        User user = userDao.getUser(username);
        return user;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
