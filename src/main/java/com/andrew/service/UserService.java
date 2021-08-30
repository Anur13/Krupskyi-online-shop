package com.andrew.service;

import com.andrew.dao.UserDao;
import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    UserDao userDao;
    SecurityService securityService;

    public void addUser(String username, String password) {
        userDao.addUser(username, securityService.encryptPassword(password));
    }

    public User getUser(String username) {
        User user = userDao.getUserByName(username);
        return user;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
