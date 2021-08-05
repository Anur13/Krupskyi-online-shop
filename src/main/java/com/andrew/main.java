package com.andrew;

import com.andrew.services.dbService;
import com.andrew.servlets.AddProductsServlet;
import com.andrew.servlets.IndexProductsServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.*;

public class main {
    public static void main(String[] args) throws Exception {
        Connection db = new dbService().connect();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        IndexProductsServlet productsServlet = new IndexProductsServlet(db);
        AddProductsServlet addProductsServlet = new AddProductsServlet(db);

        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(addProductsServlet), "/products/add");

        ResourceHandler resource_handler = new ResourceHandler();

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);

        server.setHandler(handlers);
        server.start();
    }
}
