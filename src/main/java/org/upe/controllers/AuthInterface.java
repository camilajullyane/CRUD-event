package org.upe.controllers;

import org.upe.persistence.UserInterface;
import org.upe.persistence.UserUtility;

import java.util.ArrayList;

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