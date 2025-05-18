package com.karzhou.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/double")
public class DoubleServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(DoubleServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String input = request.getParameter("number");
        logger.info("Received input for doubling: {}", input);

        try {
            int number = Integer.parseInt(input);
            int result = number * 2;

            logger.debug("Parsed number: {}, doubled result: {}", number, result);

            request.setAttribute("original", number);
            request.setAttribute("result", result);

            logger.info("Forwarding to result.jsp with original={} and result={}", number, result);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/double/result.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            logger.error("Invalid input for number: '{}'", input, e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат числа.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("GET request to /double, redirecting to form.jsp");
        response.sendRedirect(request.getContextPath() + "/double/form.jsp");
    }
}
