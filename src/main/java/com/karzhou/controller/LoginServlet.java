package com.karzhou.controller;

import com.karzhou.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Передаём сообщение об ошибке (если было) из сессии в request
        Object error = request.getSession().getAttribute("loginError");
        if (error != null) {
            request.setAttribute("error", error);
            request.getSession().removeAttribute("loginError");
        }

        request.getRequestDispatcher("/account/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        logger.info("Login attempt for username: {}", username);

        UserService userService = new UserService();

        if (userService.isUsernameAvailable(username)) {
            logger.warn("Login failed: username '{}' not found", username);
            request.getSession().setAttribute("loginError", "Invalid username or password");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!userService.checkPassword(username, password)) {
            logger.warn("Login failed: incorrect password for username '{}'", username);
            request.getSession().setAttribute("loginError", "Invalid username or password");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!userService.isEmailConfirmed(username)) {
            logger.warn("Login denied: email not confirmed for username '{}'", username);
            request.getSession().setAttribute("loginError", "Please confirm your email address before logging in");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getSession().setAttribute("user", username);

        if ("on".equals(rememberMe)) {
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 дней
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("username", "");
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }

        logger.info("User '{}' successfully logged in", username);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
