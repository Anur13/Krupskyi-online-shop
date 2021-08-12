package com.andrew.service;

import com.andrew.util.CashedPropertyReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CashedPropertyReaderTest {

    @Test
    void getProperties() throws IOException {
        String user = "user";
        String password = "root";
        String url = "jdbc:postgresql://localhost:5432/db_products";

        CashedPropertyReader cashedPropertyReader = new CashedPropertyReader();
        Properties props = cashedPropertyReader.getCashedProperties();

        assertEquals(user, props.getProperty("user"));
        assertEquals(password, props.getProperty("password"));
        assertEquals(url, props.getProperty("url"));
    }
}