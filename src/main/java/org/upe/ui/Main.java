package org.upe.ui;

import org.upe.persistence.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<User> users = User.getAllUsers();
        System.out.println(users.size());
        for (User user : users) {
            for (String eventID: user.getAttendeeOn()) {
                System.out.println(eventID);
            }
        }

        User.addAttendeeOnEvent("15524653490", "123456789");
    }
}