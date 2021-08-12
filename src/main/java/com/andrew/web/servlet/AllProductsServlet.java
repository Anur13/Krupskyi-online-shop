package com.andrew.web.servlet;


import com.andrew.util.MyLogger;
import com.andrew.web.PageGenerator;
import com.andrew.entity.Product;
import com.andrew.dao.jdbc.JdbcProductDao;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AllProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LinkedList<Product> productsList = new JdbcProductDao().getAllProducts();

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("index.html", productsList));
        response.setStatus(HttpServletResponse.SC_OK);
        new MyLogger().getLogger().info(request);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Product> productsFound = new JdbcProductDao().findProductsByNameOrDescription(query);
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("index.html", productsFound));
        response.setStatus(HttpServletResponse.SC_OK);
        new MyLogger().getLogger().info(request);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONTokener tokener = new JSONTokener(request.getReader());
        JSONObject json = new JSONObject(tokener);
        String id = json.get("id").toString();
        new JdbcProductDao().deleteProduct(id);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        new MyLogger().getLogger().info(request);
    }
}