package com.andrew.web.servlet;

import com.andrew.service.UserService;
import com.andrew.util.MyLogger;
import com.andrew.web.PageGenerator;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RegistrationServlet extends HttpServlet {
    UserService userService;
    private Logger LOGGER = new MyLogger().getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("registrationPage.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        userService.addUser(username,password);

        String cookieId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("token", cookieId);

        response.addCookie(cookie);
        LOGGER.info(request);
        response.sendRedirect("/products");

    }

    public RegistrationServlet(UserService userService) {
        this.userService = userService;
    }
}
