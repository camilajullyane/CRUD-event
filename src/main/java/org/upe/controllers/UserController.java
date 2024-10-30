package org.upe.controllers;

import org.upe.controllers.interfaces.UserControllerInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.service.UserService;
import org.upe.persistence.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class UserController implements UserControllerInterface {
    private static final UserService userService = new UserService();
    private static final EventService eventService = new EventService();

    public boolean deleteAttendeeEvent(String userCPF, String eventID) {
        userService.deleteAttendeeEvent(userCPF, eventID);
        return true;
    }

    public UserInterface getUserByCPF(String cpf) {
        return userService.findByCPF(cpf);
    }

    public ArrayList<EventInterface> userEventsIn(String ownerCPF) {
        List<Event> userEventsIn = eventService.getEventsIn(ownerCPF);
        return new ArrayList<>(userEventsIn);
    }

    public boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event) {
        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(event.getId())) {
                userService.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }

    public boolean changeEmail(String userEmail, String newEmail) {
        return userService.updateUserEmail(userEmail, newEmail);
    }

    public boolean changePassword(String cpf, String currentPassword, String newPassword) {
        return userService.updateUserPassword(cpf, currentPassword, newPassword);
    }
}
