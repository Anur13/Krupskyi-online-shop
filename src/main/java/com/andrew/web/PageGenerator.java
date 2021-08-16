package com.andrew.web;

import com.andrew.entity.Product;
import com.andrew.util.MyLogger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

public class PageGenerator {
    public static final PageGenerator PAGE_GENERATOR = new PageGenerator();
    private final Configuration configuration = new Configuration();
    private Logger LOGGER = new MyLogger().getLogger();

    public static PageGenerator getPageGenerator() {
        return PAGE_GENERATOR;
    }

    public String getPage(String filename, List<Product> data) {
        Writer stream = new StringWriter();
        Map<String, List<Product>> productsMap = new HashMap<>();
        productsMap.put("products", data);

        try {
            configuration.setClassForTemplateLoading(this.getClass(), "/templates");
            Template template = configuration.getTemplate(filename);
            template.process(productsMap, stream);

        } catch (IOException | TemplateException exception) {
            LOGGER.error(exception);
            throw new RuntimeException(exception);
        }
        return stream.toString();
    }
    public String getPage(String filename){
        return getPage(filename, new ArrayList<>());
    }
}
