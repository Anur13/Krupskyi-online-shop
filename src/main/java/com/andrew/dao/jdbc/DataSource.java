package com.andrew.dao.jdbc;

import com.andrew.util.CashedPropertyReader;
import com.andrew.util.MyLogger;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static final PGSimpleDataSource DATA_SOURCE = new PGSimpleDataSource();

    private CashedPropertyReader cashedPropertyReader = new CashedPropertyReader();
    private Properties properties = cashedPropertyReader.getCashedProperties();
    private String url = properties.getProperty("url");
    private String user = properties.getProperty("user");
    private String password = properties.getProperty("password");


    public DataSource() {
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
            new MyLogger().getLogger().error(exception);
            throw new RuntimeException("Could not establish connection " + exception);
        }
    }
}
