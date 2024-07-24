package org.upe.controllers;

import org.upe.persistence.User;
import org.upe.persistence.UserUtility;
import java.util.List;

public interface AuthInterface {

    User loginUser(String CPF);

    User signUpUser(String name, String CPF, String email);

    User createUser(String name, String CPF, String email);

    User getUserByCPF(String CPF);

    void updateUserEmail(String CPF, String newEmail);

    void deleteUser(String CPF);

    void addAttendeeOnEvent(String CPF, String eventID);

    void deleteAttendeeEvent(String CPF, String eventID);

    void deleteOwnerOf(String CPF, String eventID);

    List<User> getAllUsers();
}
