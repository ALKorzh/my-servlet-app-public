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
import java.io.OutputStream;

@WebServlet("/getPhoto")
public class GetPhotoServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(GetPhotoServlet.class);
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("user");
        logger.info("GET /getPhoto requested for user: {}", username);

        if (username == null || username.isEmpty()) {
            logger.warn("Missing or empty username parameter in request.");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username is required");
            return;
        }

        try {
            byte[] photo = userDAO.getUserPhoto(username);

            if (photo != null && photo.length > 0) {
                logger.info("Successfully retrieved photo for user: {}", username);
                resp.setContentType("image/jpeg"); // or image/png if needed
                resp.setContentLength(photo.length);
                try (OutputStream out = resp.getOutputStream()) {
                    out.write(photo);
                }
            } else {
                logger.warn("Photo not found for user: {}", username);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("Error retrieving photo for user: {}", username, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve photo.");
        }
    }
}
