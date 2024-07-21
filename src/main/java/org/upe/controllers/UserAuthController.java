package org.upe.controllers;

import org.upe.persistence.UserInterface;
import org.upe.persistence.UserUtility;

public class UserAuthController implements AuthInteface {
    UserInterface userInfo;

    public boolean loginUser(String CPF) {
        UserInterface userData = UserUtility.findByCPF(CPF);
        if (userData != null) {
            userInfo = userData;
            return true;
        }
        return false;
    }

    public void singUpUser(String name, String CPF, String email) {
        UserInterface userData = UserUtility.findByCPF(CPF);
        if (userData == null) {
            UserUtility.createUser(name, CPF, email);
            //userInfo = userData;
        }
    }
}