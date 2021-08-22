package com.andrew;

import com.andrew.dao.jdbc.JdbcProductDao;
import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.dao.jdbc.MyPGSimpleDataSource;
import com.andrew.service.ProductService;
import com.andrew.service.SecurityService;
import com.andrew.service.UserService;
import com.andrew.util.CachedPropertyReader;
import com.andrew.web.SecurityFilter;
import com.andrew.web.servlet.AddProductsServlet;
import com.andrew.web.servlet.AllProductsServlet;
import com.andrew.web.servlet.LoginServlet;
import com.andrew.web.servlet.RegistrationServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        Properties properties = new CachedPropertyReader().getCashedProperties();

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        MyPGSimpleDataSource MyPGSimpleDataSource = new MyPGSimpleDataSource(url, user, password);


        //DAO
        JdbcProductDao jdbcProductDao = new JdbcProductDao(MyPGSimpleDataSource);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(MyPGSimpleDataSource);


        //Service
        ProductService productService = new ProductService(jdbcProductDao);
        UserService userService = new UserService(jdbcUserDao);
        SecurityService securityService = new SecurityService(userService);

        userService.setSecurityService(securityService);
        //Servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        AllProductsServlet productsServlet = new AllProductsServlet(productService);
        AddProductsServlet addProductsServlet = new AddProductsServlet(productService);
        LoginServlet loginServlet = new LoginServlet(securityService);
        RegistrationServlet registrationServlet = new RegistrationServlet(userService);

        context.addServlet(new ServletHolder(registrationServlet), "/register");
        context.addServlet(new ServletHolder(productsServlet), "/products");
        context.addServlet(new ServletHolder(addProductsServlet), "/products/add");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        //Filter
        SecurityFilter securityFilter = new SecurityFilter(securityService);
        context.addFilter(new FilterHolder(securityFilter),"/*", EnumSet.of(DispatcherType.REQUEST));


        Server server = new Server(8080);

        server.setHandler(context);
        server.start();
        server.join();
    }
}
