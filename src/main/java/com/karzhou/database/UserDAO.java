package com.karzhou.database;

import com.karzhou.entity.User;

import java.io.InputStream;
import java.sql.*;

public class UserDAO {

    public User findUserByUsername(String username) {
        String query = "SELECT username, password, email, confirmation_token, confirmed FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("confirmation_token"),
                        rs.getBoolean("confirmed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(User user) {
        String query = "INSERT INTO users (username, password, email, confirmation_token, confirmed) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getConfirmationToken());
            statement.setBoolean(5, false);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findUserByToken(String token) {
        String query = "SELECT username, password, email, confirmation_token, confirmed FROM users WHERE confirmation_token = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("confirmation_token"),
                        rs.getBoolean("confirmed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean confirmUserByToken(String token) {
        String query = "UPDATE users SET confirmed = TRUE, confirmation_token = NULL WHERE confirmation_token = ? AND confirmed = FALSE";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, token);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPassword(String username) {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setConfirmedStatus(String username, boolean confirmed) {
        String query = "UPDATE users SET confirmed = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, confirmed);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public byte[] getUserPhoto(String username) {
        String sql = "SELECT photo FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Blob blob = rs.getBlob("photo");
                if (blob != null) {
                    return blob.getBytes(1, (int) blob.length());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserPhoto(String username, InputStream photoStream, long photoLength) {
        String query = "UPDATE users SET photo = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (photoStream != null) {
                statement.setBlob(1, photoStream, photoLength);
            } else {
                statement.setNull(1, Types.BLOB);  // Удалить фото, если передан null
            }
            statement.setString(2, username);
            int updated = statement.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUsername(String oldUsername, String newUsername) {
        String query = "UPDATE users SET username = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newUsername);
            statement.setString(2, oldUsername);
            int updated = statement.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
