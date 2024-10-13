package org.upe.controllers;

import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.UserUtility;

public interface AuthController {
    static UserInterface loginUser(String cpf, String password) {
        return UserUtility.AuthUser(cpf, password);
    }

    static UserInterface signUpUser(String name, String cpf, String email, String password) {
        return UserUtility.createUser(name, email, cpf, password);
    }
}