package com.andrew.util;

import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;


public class CachedPropertyReader {
    private final String DEFAULT_CONFIG_PATH = "db/dbResources/dbConfig.properties";
    private Logger LOGGER = new MyLogger().getLogger();
    private Properties cashedProperties = new Properties();


    public CachedPropertyReader(String path) {
        readProperties(path);
    }
    public CachedPropertyReader() {
        readProperties(DEFAULT_CONFIG_PATH);
    }
    public Properties getCashedProperties() {
        return new Properties(cashedProperties);
    }

    public void readProperties(String path) {
        try (InputStream input = CachedPropertyReader.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found at " + path);
            }
            cashedProperties.load(input);
        } catch (IOException exception) {
            LOGGER.error(exception);
            throw new RuntimeException("Could not access properties" + exception);
        }
    }

}
