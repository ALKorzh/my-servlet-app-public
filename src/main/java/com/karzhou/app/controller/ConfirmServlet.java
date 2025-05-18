package com.karzhou.app.controller;

import com.karzhou.app.database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ConfirmServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        logger.info("Received confirmation request with token: {}", token);

        if (token == null || token.isEmpty()) {
            logger.warn("Confirmation token is missing or empty");
            response.sendRedirect(request.getContextPath() + "/account/confirmation_failed.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        logger.debug("Attempting to confirm user with token: {}", token);
        boolean success = userDAO.confirmUserByToken(token);

        if (success) {
            logger.info("User successfully confirmed with token: {}", token);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            logger.warn("User confirmation failed for token: {}", token);
            response.sendRedirect(request.getContextPath() + "/account/confirmation_failed.jsp");
        }
    }

}
