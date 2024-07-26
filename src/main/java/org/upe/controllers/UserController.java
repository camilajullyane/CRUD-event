package org.upe.controllers;

import org.upe.persistence.*;

import java.util.ArrayList;

public interface UserController {
    static boolean deleteAttendeeEvent(String CPF, String eventID) {
        boolean user = UserUtility.deleteAttendeeEvent(CPF, eventID);

        return true;
    }

    static ArrayList<EventInterface> userEventsIn(String ownerCPF) {
        ArrayList<Event> userEventsIn = EventUtility.getEventsIn(ownerCPF);

        return new ArrayList<EventInterface>(userEventsIn);
    }

    static boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event) {
        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(event.getId())) {
                UserUtility.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }
}