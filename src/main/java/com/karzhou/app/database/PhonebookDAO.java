package com.karzhou.app.database;

import com.karzhou.app.entity.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhonebookDAO {
    private static final Logger logger = LogManager.getLogger(PhonebookDAO.class);


    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT last_name, phone_number FROM phonebook ORDER BY last_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone_number");
                contacts.add(new Contact(lastName, phoneNumber));
            }
        }

        return contacts;
    }
    public void addContact(Contact contact) throws SQLException {
        String sql = "INSERT INTO phonebook (last_name, phone_number) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getLastName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.executeUpdate();

            logger.info("Contact added: " + contact.getLastName() + ", " + contact.getPhoneNumber());
        }
    }
}

