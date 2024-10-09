package org.upe.controllers;

import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.UserUtility;

public interface AuthInterface  {
    static UserInterface loginUser(String CPF) {
        UserInterface userData = UserUtility.findByCPF(CPF);
        if (userData != null) {
            return userData;
        }
        return null;
    }

    static UserInterface signUpUser(String name, String CPF, String email) {
        UserInterface userData = UserUtility.createUser(name, CPF, email);

        if(userData != null) {
            return userData;
        }
        return null;
    }
}