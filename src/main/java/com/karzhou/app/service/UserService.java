package com.karzhou.app.service;

import com.karzhou.app.database.UserDAO;
import com.karzhou.app.entity.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String username, String password) {
        String hashedPassword = userDAO.getPassword(username);
        if (hashedPassword == null) return false;
        return BCrypt.checkpw(password, hashedPassword);
    }

    public boolean isUsernameAvailable(String username) {
        User user = userDAO.findUserByUsername(username);
        return user == null;
    }

    public void registerUser(User user) {
        if (!isUsernameAvailable(user.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        user.setConfirmed(false);

        userDAO.addUser(user);
    }

    public boolean isEmailConfirmed(String username) {
        User user = userDAO.findUserByUsername(username);
        return user != null && user.isConfirmed();
    }

    public void confirmEmail(String username) {
        userDAO.setConfirmedStatus(username, true);
    }
}
