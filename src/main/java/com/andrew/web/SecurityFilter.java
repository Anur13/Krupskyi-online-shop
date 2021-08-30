package com.andrew.web;


import com.andrew.security.Session;
import com.andrew.service.SecurityService;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends GenericFilterBean {
    SecurityService securityService;
    WebApplicationContext webApplicationContext;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    public SecurityFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        if (webApplicationContext == null) {
            ServletContext servletContext = request.getServletContext();
            webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        }

        securityService = webApplicationContext.getBean(SecurityService.class);

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getRequestURI();
        if (path.equals("/login") || path.equals("/register")) {
            chain.doFilter(request, response);
            return;
        }
        String token = CookieParser.getTokenFromCookies(httpServletRequest.getCookies());
//        System.out.println(token);
        Session session = securityService.getSession(token);
//        System.out.println(session);
        if (session != null) {
            httpServletRequest.setAttribute("session", session);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.sendRedirect("/login");
    }


}
