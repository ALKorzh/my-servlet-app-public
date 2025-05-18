package com.karzhou.app.controller;

import com.karzhou.app.database.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/profile")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class ProfileServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ProfileServlet.class);
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Unauthorized access attempt to /profile");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("user");
        String action = req.getParameter("action");

        switch (action) {
            case "uploadPhoto" -> {
                Part photoPart = req.getPart("photo");
                if (photoPart != null && photoPart.getSize() > 0) {
                    try (InputStream inputStream = photoPart.getInputStream()) {
                        boolean success = userDAO.updateUserPhoto(username, inputStream, photoPart.getSize());
                        if (success) {
                            session.setAttribute("message", "Photo uploaded successfully.");
                            logger.info("User '{}' uploaded a new photo (size: {} bytes)", username, photoPart.getSize());
                        } else {
                            session.setAttribute("error", "Failed to upload photo.");
                            logger.error("User '{}' failed to upload photo", username);
                        }
                    }
                } else {
                    session.setAttribute("error", "No photo selected.");
                    logger.warn("User '{}' submitted upload without a photo", username);
                }
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
            case "deletePhoto" -> {
                boolean success = userDAO.updateUserPhoto(username, null, 0);
                if (success) {
                    session.setAttribute("message", "Photo deleted successfully.");
                    logger.info("User '{}' deleted their photo", username);
                } else {
                    session.setAttribute("error", "Failed to delete photo.");
                    logger.error("User '{}' failed to delete photo", username);
                }
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
            case "updateUsername" -> {
                String newUsername = req.getParameter("newUsername");
                if (newUsername == null || newUsername.trim().isEmpty()) {
                    session.setAttribute("error", "Username cannot be empty.");
                    logger.warn("User '{}' attempted to update username with empty value", username);
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                }

                newUsername = newUsername.trim();

                if (newUsername.equals(username)) {
                    session.setAttribute("message", "Username is unchanged.");
                    logger.info("User '{}' submitted unchanged username", username);
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                }

                if (userDAO.findUserByUsername(newUsername) != null) {
                    session.setAttribute("error", "Username already exists.");
                    logger.warn("User '{}' attempted to update username to existing username '{}'", username, newUsername);
                    resp.sendRedirect(req.getContextPath() + "/profile");
                    return;
                }

                boolean updateSuccess = userDAO.updateUsername(username, newUsername);
                if (updateSuccess) {
                    session.setAttribute("user", newUsername);
                    session.setAttribute("message", "Username updated successfully.");
                    logger.info("User '{}' changed username to '{}'", username, newUsername);
                } else {
                    session.setAttribute("error", "Failed to update username.");
                    logger.error("User '{}' failed to update username to '{}'", username, newUsername);
                }
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
            default -> {
                logger.warn("User '{}' sent invalid action '{}' to /profile", username, action);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Object message = session.getAttribute("message");
        Object error = session.getAttribute("error");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }
        if (error != null) {
            req.setAttribute("error", error);
            session.removeAttribute("error");
        }

        req.getRequestDispatcher("/account/profile.jsp").forward(req, resp);
    }
}
