package com.karzhou.controller;

import com.karzhou.entity.User;
import com.karzhou.service.EmailService;
import com.karzhou.service.UserService;
import com.karzhou.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object error = session.getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                session.removeAttribute("error");
            }
            Object message = session.getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message");
            }
        }
        request.getRequestDispatcher("/account/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        if (username == null || username.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
            session.setAttribute("error", "All fields are required.");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        UserService userService = new UserService();

        if (!userService.isUsernameAvailable(username)) {
            session.setAttribute("error", "Username is already taken.");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        try {
            UserValidator validator = new UserValidator();
            String confirmationToken = UUID.randomUUID().toString();
            User user = new User(username, password, email, confirmationToken, false);

            validator.validate(user);
            userService.registerUser(user);

            EmailService emailService = new EmailService();
            emailService.sendConfirmationEmail(email, confirmationToken);

            logger.info("User '{}' registered successfully. Confirmation email sent to '{}'.", username, email);

            session.setAttribute("message", "Registration successful. Please check your email to confirm your account.");
            response.sendRedirect(request.getContextPath() + "/check_email");
        } catch (Exception e) {
            logger.error("Registration failed for user '{}': {}", username, e.getMessage(), e);
            session.setAttribute("error", "Registration failed: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/register");
        }
    }
}
