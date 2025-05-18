package com.karzhou.app.controller;

import com.karzhou.app.database.PhonebookDAO;
import com.karzhou.app.entity.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/add-contact")
public class AddContactServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddContactServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        logger.info("Received request to add contact. lastName={}, phoneNumber={}", lastName, phoneNumber);

        if (lastName == null || phoneNumber == null || lastName.isEmpty() || phoneNumber.isEmpty()) {
            logger.warn("Invalid input: lastName or phoneNumber is empty or null");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        Contact contact = new Contact(lastName, phoneNumber);
        PhonebookDAO dao = new PhonebookDAO();

        try {
            logger.debug("Attempting to add contact to the database: {}", contact);
            dao.addContact(contact);
            logger.info("Contact successfully added: {}", contact);

            List<Contact> allContacts = dao.getAllContacts();
            logger.debug("Retrieved {} contacts from database", allContacts.size());

            request.setAttribute("contacts", allContacts);
            request.getRequestDispatcher("/phonebook/success.jsp").forward(request, response);

        } catch (SQLException e) {
            logger.error("Failed to add or retrieve contacts", e);
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("Redirecting to add contact form");
        response.sendRedirect(request.getContextPath() + "/phonebook/add.jsp");
    }
}
