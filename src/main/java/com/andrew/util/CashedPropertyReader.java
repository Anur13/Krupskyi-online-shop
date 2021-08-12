package com.andrew.util;

import java.io.*;
import java.util.Properties;


public class CashedPropertyReader {
    private Properties cashedProperties = new Properties();
    private final String DEFAULT_CONFIG_PATH = "db/dbResources/dbConfig.properties";


    public CashedPropertyReader(String path) {
        readProperties(path);
    }
    public CashedPropertyReader() {
        readProperties(DEFAULT_CONFIG_PATH);
    }
    public Properties getCashedProperties() {
        return new Properties(cashedProperties);
    }

    public void readProperties(String path) {
        try (InputStream input = CashedPropertyReader.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found at " + path);
            }
            cashedProperties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException("Could not access properties" + exception);
        }
    }

}
