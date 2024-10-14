package org.upe.utils;

import org.upe.persistence.interfaces.UserInterface;

public class UserSession {
    private static UserSession instance;
    private UserInterface currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(UserInterface user) {
        currentUser = user;
    }

    public UserInterface getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}