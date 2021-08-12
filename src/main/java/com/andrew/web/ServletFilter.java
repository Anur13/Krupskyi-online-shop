package com.andrew.web;

import com.andrew.dao.jdbc.JdbcUserDao;
import com.andrew.entity.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServletFilter implements Filter {
    private boolean isValid = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        List<String> storedCookiesList = CookiesManager.getCookiesList();

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                for (String storedCookie : storedCookiesList) {
                    if ("token".equals(cookie.getName())) {
                        if (storedCookie.equals(cookie.getValue())) {
                            isValid = true;
                        }
                        break;
                    }
                }
            }
        }
        if (!isValid) {
            httpServletResponse.sendRedirect("/products/login");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
