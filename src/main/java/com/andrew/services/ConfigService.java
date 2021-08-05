package com.andrew.services;

import com.andrew.utils.ResourceFileStream;

import java.io.*;
import java.util.Properties;


public class ConfigService {
    Properties props = new Properties();

    Properties getConfigProperties() throws IOException {
        try (InputStream input = new ResourceFileStream().getStream("dbResources/dbConfig.properties");) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found!");
            }
            props.load(input);
            return props;
        }
    }

}
