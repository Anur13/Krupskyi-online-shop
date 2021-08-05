package com.andrew.utils;

import com.andrew.servlets.AddProductsServlet;

import java.io.InputStream;

public class ResourceFileStream {

    public InputStream getStream(String path) {
        return AddProductsServlet.class.getClassLoader()
                .getResourceAsStream(path);
    }
}
