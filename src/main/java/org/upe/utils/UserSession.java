package org.upe.utils;

import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.UserInterface;

public class UserSession {
    private static UserSession instance;
    @Getter @Setter
    private static UserInterface currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

//    public void setCurrentUser(UserInterface user) {
//        currentUser = user;
//    }
//
//    public static UserInterface getCurrentUser() {
//        return currentUser;
//    }

    public void clearSession() {
        currentUser = null;
    }
}