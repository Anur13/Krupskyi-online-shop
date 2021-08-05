package com.andrew.servlets;

import com.andrew.methods.DbMethods;
import com.andrew.services.ConfigService;
import com.andrew.utils.ResourceFileStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AddProductsServlet extends HttpServlet {
    Connection connection;

    public AddProductsServlet(Connection connection) {
        this.connection = connection;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try (InputStream input = new ResourceFileStream().getStream("templates/addProductPage.html");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));) {
            String line;
            while ((line = reader.readLine()) != null) {
                resp.getWriter().println(line);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));

        try (InputStream input = new ResourceFileStream().getStream("templates/addProductResponse.html");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));) {

            new DbMethods(connection).addProduct("products", name, price);

            String line;
            while ((line = reader.readLine()) != null) {
                resp.getWriter().println(line);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
