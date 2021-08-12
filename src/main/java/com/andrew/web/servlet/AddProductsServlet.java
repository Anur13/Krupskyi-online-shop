package com.andrew.web.servlet;

import com.andrew.dao.jdbc.JdbcProductDao;
import com.andrew.util.MyLogger;
import com.andrew.web.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class AddProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("addProductPage.html"));
        response.setStatus(HttpServletResponse.SC_OK);
        new MyLogger().getLogger().info(request);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        String description = request.getParameter("description");


        new JdbcProductDao().addProduct(name, price,description);
        new MyLogger().getLogger().info(request);

        response.sendRedirect("/products");

    }
}
