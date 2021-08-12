package com.andrew;

import com.andrew.web.ServletFilter;
import com.andrew.web.servlet.AddProductsServlet;
import com.andrew.web.servlet.AllProductsServlet;
import com.andrew.web.servlet.LoginServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class main {
    public static void main(String[] args) throws Exception {



        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        AllProductsServlet productsServlet = new AllProductsServlet();
        AddProductsServlet addProductsServlet = new AddProductsServlet();
        LoginServlet loginServlet = new LoginServlet();


        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(addProductsServlet), "/products/add");
        context.addServlet(new ServletHolder(loginServlet), "/products/login");

        context.addFilter(ServletFilter.class, "/products", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(ServletFilter.class, "/products/add", EnumSet.of(DispatcherType.REQUEST));


        Server server = new Server(8080);

        server.setHandler(context);
        server.start();
        server.join();
    }
}
