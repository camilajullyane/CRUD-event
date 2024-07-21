package org.upe.controllers;

import org.upe.persistence.UserInterface;
import org.upe.persistence.UserUtility;

public class Login implements LoginInterface {
    UserInterface userInfo;

    public boolean loginUser(String CPF) {
        UserInterface userData = UserUtility.findByCPF(CPF);
        if (userData != null) {
            userInfo = userData;
            return true;
        }
        return false;
    }
}