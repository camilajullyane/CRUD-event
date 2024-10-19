package org.upe.controllers;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final UserUtility userUtility = new UserUtility();
    private static final EventUtility eventUtility = new EventUtility();

    static boolean deleteAttendeeEvent(String userCPF, String eventID) {
        userUtility.deleteAttendeeEvent(userCPF, eventID);
        return true;
    }

    static ArrayList<EventInterface> userEventsIn(String ownerCPF) {
        List<Event> userEventsIn = eventUtility.getEventsIn(ownerCPF);
        return new ArrayList<>(userEventsIn);
    }

    static boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event) {
        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(event.getId())) {
                userUtility.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }
}
