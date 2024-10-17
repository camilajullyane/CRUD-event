package org.upe.controllers;

import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.UserUtility;

public class AuthController {
    private static final UserUtility userUtility = new UserUtility();

    public UserInterface loginUser(String cpf, String password) {
        return userUtility.authUser(cpf, password);
    }

    public UserInterface signUpUser(String name, String cpf, String email, String password) {
        return userUtility.createUser(name, email, cpf, password);
    }
}