package com.andrew.services;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigServiceTest {

    @Test
    void getProperties() throws IOException {
        String user = "user";
        String password = "root";
        String url = "jdbc:postgresql://localhost:5432/db_products";

        ConfigService configService = new ConfigService();
        Properties props = configService.getConfigProperties();

        assertEquals(user, props.getProperty("user"));
        assertEquals(password, props.getProperty("password"));
        assertEquals(url, props.getProperty("url"));
    }
}