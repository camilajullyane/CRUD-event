package org.upe.persistence.service;

import org.upe.persistence.model.User;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.UserUtility;

import java.util.List;

public class UserService {
    private static final UserUtility userUtility = new UserUtility();

    public List<User> getAllUsers() {
        return userUtility.getAllUsers();
    }

    public User findByCPF(String cpf) {
        return userUtility.findByCPF(cpf);
    }

    public User findByEmail(String email) {
        return userUtility.findByEmail(email);
    }

    public UserInterface createUser(String name, String email, String cpf, String password) {
        if(findByCPF(cpf) != null) {
            return null;
        }

        if(findByEmail(email) != null) {
            return null;
        }

        return userUtility.createUser(name, email, cpf, password);
    }

    public boolean updateUserEmail(String email, String newEmail) {

        if (userUtility.findByEmail(newEmail) != null) {
            return false;
        }

        return userUtility.updateUserEmail(email, newEmail);
    }

    public boolean updateUserPassword(String cpf, String currentPassword, String newPassword) {
        return userUtility.updateUserPassword(cpf, currentPassword, newPassword);
    }

    public void deleteUser(String cpf) {
        userUtility.deleteUser(cpf);
    }

    public boolean addAttendeeOnEvent(UserInterface user, String eventID) {
        for (String ownerOf : user.getOwnerOf()) {
            if (ownerOf.equals(eventID)) {
                return false;
            }
        }

        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(eventID)) {
                return false;
            }
        }

        return userUtility.addAttendeeOnEvent(user, eventID);
    }

    public void addOwnerOnEvent(String userCPF, String eventID) {
        userUtility.addOwnerOnEvent(userCPF, eventID);
    }

    //fazer regras de negócio
    public boolean deleteAllAttendeesFromEvent(String eventID) {
        return userUtility.deleteAllAttendeesFromEvent(eventID);
    }

    public boolean deleteAttendeeEvent(String cpf, String eventID) {
        return userUtility.deleteAttendeeEvent(cpf, eventID);
    }

    public void deleteOwnerOf(String cpf, String eventID) {
        userUtility.deleteOwnerOf(cpf, eventID);
    }

    public void addUserArticle(String cpf, String articleID) {
        userUtility.addUserArticle(cpf, articleID);
    }

    public UserInterface authUser(String cpf, String password) {
        User user = findByCPF(cpf);
        if (user == null) {
            return null;
        }

        if(user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

