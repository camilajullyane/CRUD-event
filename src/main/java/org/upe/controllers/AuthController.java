package org.upe.controllers;

import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.UserUtility;

public interface AuthController {
    static UserInterface loginUser(String cpf) {
        UserInterface userData = UserUtility.findByCPF(cpf);
        if (userData != null) {
            return userData;
        }
        return null;
    }

    static UserInterface signUpUser(String name, String cpf, String email) {
        UserInterface userData = UserUtility.createUser(name, email, cpf);
        return userData;
    }
}