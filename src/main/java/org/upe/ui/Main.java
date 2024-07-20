package org.upe.ui;

import org.upe.persistence.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<User> users = User.getAllUsers();
        System.out.println(users.size());
        for (User user : users) {
            System.out.println(user.getName());
        }

        User.deleteUser("65465657");
    }
}