package com.andrew.web;

import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.entity.User;
import com.andrew.security.Session;
import com.andrew.service.SecurityService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SecurityFilter implements Filter {
    private boolean isValid = false;
    SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getRequestURI();
        if (path.equals("/login") || path.equals("/register")) {
            chain.doFilter(request, response);
            return;
        }
        String token = CookieParser.getTokenFromCookies(httpServletRequest.getCookies());
        Session session = securityService.getSession(token);
        if (session != null) {
            httpServletRequest.setAttribute("session", session);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.sendRedirect("/login");
    }

    @Override
    public void destroy() {

    }
}
