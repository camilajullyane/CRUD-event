package org.upe.controllers;

import org.upe.controllers.interfaces.UserControllerInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.oldModel.Event;
import org.upe.persistence.oldModel.User;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;

import java.util.ArrayList;
import java.util.List;

public class UserController implements UserControllerInterface {
    private static final UserUtility userUtility = new UserUtility();
    private static final EventUtility eventUtility = new EventUtility();

    public UserInterface getUserByCPF(String cpf) {
        return userUtility.findByCPF(cpf);
    }

    public ArrayList<EventInterface> userEventsIn(String ownerCPF) {
        List<Event> userEventsIn = eventUtility.getEventsIn(ownerCPF);
        return new ArrayList<>(userEventsIn);
    }

    public boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event) {
        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(event.getId())) {
                userUtility.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }

    public boolean changePassword(String cpf, String currentPassword, String newPassword) {
        return userUtility.updateUserPassword(cpf, currentPassword, newPassword);
    }


    public User findByCPF(String cpf) {
        return userUtility.findByCPF(cpf);
    }

    public boolean changeEmail(String email, String newEmail) {

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

}
