package com.andrew;

import com.andrew.dao.jdbc.JdbcProductDao;
import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.dao.jdbc.MyPGSimpleDataSource;
import com.andrew.util.CachedPropertyReader;
import com.andrew.web.ServletFilter;
import com.andrew.web.servlet.AddProductsServlet;
import com.andrew.web.servlet.AllProductsServlet;
import com.andrew.web.servlet.LoginServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        Properties properties = new CachedPropertyReader().getCashedProperties();

        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        MyPGSimpleDataSource MyPGSimpleDataSource = new MyPGSimpleDataSource(url, user, password);

        JdbcProductDao jdbcProductDao = new JdbcProductDao(MyPGSimpleDataSource);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(MyPGSimpleDataSource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        AllProductsServlet productsServlet = new AllProductsServlet(jdbcProductDao);
        AddProductsServlet addProductsServlet = new AddProductsServlet(jdbcProductDao);
        LoginServlet loginServlet = new LoginServlet(jdbcUserDao);


        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(addProductsServlet), "/products/add");
        context.addServlet(new ServletHolder(loginServlet), "/login");

        context.addFilter(ServletFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

//      TODO: Figure out how to use js files as resources

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("templates/js");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
