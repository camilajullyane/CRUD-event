package org.upe.controllers;

import org.upe.controllers.interfaces.AuthControllerInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;
import org.upe.persistence.repository.UserUtility;

public class AuthController implements AuthControllerInterface {
    private static final UserUtility userUtility = new UserUtility();

    public UserInterface loginUser(String cpf, String password) {
        User user = userUtility.findByCPF(cpf);
        if (user == null) {
            return null;
        }

        if(user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public UserInterface signUpUser(String name, String email, String cpf, String password) {
        if(userUtility.findByCPF(cpf) != null) {
            return null;
        }

        if(userUtility.findByEmail(email) != null) {
            return null;
        }

        return userUtility.createUser(name, email, cpf, password);
    }
}