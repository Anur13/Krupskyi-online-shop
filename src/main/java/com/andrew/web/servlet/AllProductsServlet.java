package com.andrew.web.servlet;


import com.andrew.util.MyLogger;
import com.andrew.web.PageGenerator;
import com.andrew.entity.Product;
import com.andrew.dao.jdbc.JdbcProductDao;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AllProductsServlet extends HttpServlet {
    private JdbcProductDao jdbcProductDao;
    private Logger LOGGER = new MyLogger().getLogger();

    public AllProductsServlet(JdbcProductDao jdbcProductDao) {
        this.jdbcProductDao = jdbcProductDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LinkedList<Product> productsList = jdbcProductDao.getAllProducts();

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("index.html", productsList));
        response.setStatus(HttpServletResponse.SC_OK);
        LOGGER.info(request);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Product> productsFound = jdbcProductDao.findProductsByNameOrDescription(query);
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("index.html", productsFound));
        response.setStatus(HttpServletResponse.SC_OK);
        LOGGER.info(request);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONTokener tokener = new JSONTokener(request.getReader());
        JSONObject json = new JSONObject(tokener);
        String id = json.get("id").toString();
        jdbcProductDao.deleteProduct(id);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        LOGGER.info(request);
    }
}