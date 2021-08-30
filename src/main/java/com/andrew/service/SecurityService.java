package com.andrew.service;

import com.andrew.entity.Product;
import com.andrew.entity.User;
import com.andrew.security.PasswordEncryptor;
import com.andrew.security.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

public class SecurityService {
    private final Map<String, Session> SESSION_LIST = Collections.synchronizedMap(new HashMap<>());
    @Autowired
    UserService userService;
    private PasswordEncryptor passwordEncryptor;

    public List<Product> getCartByToken(String token) {
        return SESSION_LIST.get(token).getCart();
    }


    public String encryptPassword(String password) {
        byte[] hash = passwordEncryptor.encryptPassword(password);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(String.valueOf(b));
        }
        return stringBuilder.toString();
    }

    public Session getSession(String token) {
        System.out.println(SESSION_LIST + "getSession");
        if (token != null) {
            Session session = SESSION_LIST.get(token);
            if (isTokenValid(token)) {
                return session;
            }
        }
        return null;
    }

    private boolean isPasswordValid(String submittedPassword, String passwordFromDb) {
        String encryptedPassword = encryptPassword(submittedPassword);
        return encryptedPassword.equals(passwordFromDb);
    }

    public String login(String username, String submittedPassword) {
        User user = userService.getUser(username);
        String passwordFromDb = user.getPassword();
        if (isPasswordValid(submittedPassword, passwordFromDb)) {
            return generateAndAddToken(user);
        }
        return null;
    }

    private boolean isTokenValid(String token) {
        Session session = SESSION_LIST.get(token);
        if (session == null) {
            return false;
        }
        if (session.getExpireDate().isBefore(LocalDateTime.now())) {
            SESSION_LIST.remove(token);
            return false;
        }
        return true;
    }

    private String generateAndAddToken(User user) {
        String uuid = UUID.randomUUID().toString();
        Session session = new Session(LocalDateTime.now().plusHours(10000 / 3600), new ArrayList<>(), user);
        SESSION_LIST.put(uuid, session);
        System.out.println(SESSION_LIST + "generateAndAddToken");
        return uuid;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }
}

