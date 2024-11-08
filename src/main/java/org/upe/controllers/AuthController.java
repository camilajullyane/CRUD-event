package org.upe.controllers;

import org.upe.controllers.interfaces.AuthControllerInterface;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

public class AuthController implements AuthControllerInterface {
    private static final UserDAO userDAO = new UserDAO();

    public UserInterface loginUser(String cpf, String password) {
        User user = userDAO.findByCPF(cpf);
        if (user == null) {
            return null;
        }

        if(user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public UserInterface signUpUser(String name, String cpf, String email, String password) {
        if(userDAO.findByCPF(cpf) != null) {
            return null;
        }

        if(userDAO.findByEmail(email) != null) {
            return null;
        }

        return userDAO.create(name, email, cpf, password);
    }
}