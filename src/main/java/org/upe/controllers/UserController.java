package org.upe.controllers;

import org.upe.controllers.interfaces.UserControllerInterface;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

public class UserController implements UserControllerInterface {
    private static final UserDAO userDAO = new UserDAO();

    public UserInterface getUserByCPF(String cpf) {
        return userDAO.findByCPF(cpf);
    }

    public boolean changePassword(UserInterface user, String currentPassword, String newPassword) {
        User utilityUser = userDAO.findByCPF(user.getCpf());

        if(utilityUser != null && utilityUser.getPassword().equals(currentPassword)) {
            utilityUser.setPassword(newPassword);
            userDAO.update(utilityUser);
            return true;
        }

        return false;
    }

    public boolean changeEmail(String email, String newEmail) {
        User utilityUser = userDAO.findByEmail(email);

        if (utilityUser == null) {
            return false;
        }

        utilityUser.setEmail(newEmail);
        userDAO.update(utilityUser);
        return true;
    }

    public void deleteUser(UserInterface user) {
        userDAO.delete(user);
    }
}
