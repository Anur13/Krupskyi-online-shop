package com.andrew.web.servlet;

import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.entity.User;
import com.andrew.service.SecurityService;
import com.andrew.service.UserService;
import com.andrew.util.MyLogger;
import com.andrew.web.PageGenerator;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private Logger LOGGER = new MyLogger().getLogger();
    private SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPageGenerator().getPage("loginPage.html"));
        response.setStatus(HttpServletResponse.SC_OK);
        LOGGER.info(request);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String token = securityService.login(username, password);
        if (token != null) {
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            LOGGER.info(request);

            response.sendRedirect("/products");

        }

    }
}
