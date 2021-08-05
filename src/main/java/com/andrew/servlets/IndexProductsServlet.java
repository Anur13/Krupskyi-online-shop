package com.andrew.servlets;


import com.andrew.PageGenerator;
import com.andrew.entities.Product;
import com.andrew.methods.DbMethods;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class IndexProductsServlet extends HttpServlet {
    Connection connection;
    Map<String, LinkedList<Product>> productsMap;

    public IndexProductsServlet(Connection conn) {
        connection = conn;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            productsMap = new DbMethods(connection).getAllProductsMap("products");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.getWriter().println(PageGenerator.instance().getPage("index.ftl", productsMap));
        resp.setStatus(HttpServletResponse.SC_OK);

    }


//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");
//        int price = Integer.parseInt(req.getParameter("price"));
//
//        try {
//            productsMap = new DbMethods(connection).addProduct("products", name, price);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        resp.getWriter().println(PageGenerator.instance().getPage("index.ftl", productsMap));
//        resp.setStatus(HttpServletResponse.SC_OK);
//
//    }
}