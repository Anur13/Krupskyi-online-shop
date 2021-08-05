package com.andrew.services;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class dbService {
    ConfigService configService = new ConfigService();

    Properties props = configService.getConfigProperties();
    String url = props.getProperty("url");
    String user = props.getProperty("user");
    String password = props.getProperty("password");

    Connection connection;

    public dbService() throws IOException {
    }
    public Connection connect() throws SQLException {
        return connection = DriverManager.getConnection(url, user, password);
    }
}
