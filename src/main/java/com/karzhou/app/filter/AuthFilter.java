package com.karzhou.app.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Logger logger =  LogManager.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean loggedIn = (session != null) && (session.getAttribute("user") != null);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        boolean isLoginRequest = path.startsWith("/login");
        boolean isRegisterRequest = path.startsWith("/register");
        boolean isResourceRequest = path.startsWith("/static/")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/account/confirmation_failed.jsp");
        boolean isExcludedPath = path.startsWith("/double")
                || path.startsWith("/add-contact")
                || path.startsWith("/confirm");

        logger.debug("Request URI: {}, Logged in: {}, Allowed without auth: {}", path, loggedIn,
                (isLoginRequest || isRegisterRequest || isResourceRequest || isExcludedPath));

        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        if (!isResourceRequest) {
            httpResponse.setContentType("text/html; charset=UTF-8");
        }

        if (loggedIn || isLoginRequest || isRegisterRequest || isResourceRequest || isExcludedPath) {
            chain.doFilter(request, response);
            logger.debug("Request allowed: {}", path);
        } else {
            logger.info("Unauthorized access attempt to: {}. Redirecting to login.", path);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
        logger.info("AuthFilter destroyed");
    }
}
