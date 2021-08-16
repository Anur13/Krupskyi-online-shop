package com.andrew.dao.jdbc;

import com.andrew.util.MyLogger;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class MyPGSimpleDataSource {
    private static final org.postgresql.ds.PGSimpleDataSource DATA_SOURCE = new org.postgresql.ds.PGSimpleDataSource();
    private Logger LOGGER = new MyLogger().getLogger();

    private String url;
    private String user;
    private String password;

    public MyPGSimpleDataSource(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        configureDataSource();
    }

    public MyPGSimpleDataSource() {
        configureDataSource();
    }

    private void configureDataSource() {
        DATA_SOURCE.setPassword(password);
        DATA_SOURCE.setUser(user);
        DATA_SOURCE.setURL(url);
    }

    public Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Could not establish connection " , exception);
        }
    }
}
