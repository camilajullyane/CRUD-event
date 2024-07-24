package org.upe.controllers;

import org.upe.persistence.User;
import org.upe.persistence.UserUtility;
import java.util.List;

public class UserController implements AuthInterface {

    @Override
    public User loginUser(String CPF) {
        return (User) UserUtility.findByCPF(CPF);
    }

    @Override
    public User signUpUser(String name, String CPF, String email) {
        return (User) UserUtility.createUser(name, CPF, email);
    }

    @Override
    public User createUser(String name, String CPF, String email) {
        return (User) UserUtility.createUser(name, CPF, email);
    }

    @Override
    public User getUserByCPF(String CPF) {
        return (User) UserUtility.findByCPF(CPF);
    }

    @Override
    public void updateUserEmail(String CPF, String newEmail) {
        UserUtility.updateUserEmail(CPF, newEmail);
    }

    @Override
    public void deleteUser(String CPF) {
        UserUtility.deleteUser(CPF);
    }

    @Override
    public void addAttendeeOnEvent(String CPF, String eventID) {
        UserUtility.addAttendeeOnEvent(CPF, eventID);
    }

    @Override
    public void deleteAttendeeEvent(String CPF, String eventID) {
        UserUtility.deleteAttendeeEvent(CPF, eventID);
    }

    @Override
    public void deleteOwnerOf(String CPF, String eventID) {
        UserUtility.deleteOwnerOf(CPF, eventID);
    }

    @Override
    public List<User> getAllUsers() {
        return UserUtility.getAllUsers();
    }
}
