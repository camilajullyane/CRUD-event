package org.upe.controllers;

import org.upe.persistence.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.ArrayList;

public interface EventController {
    static EventInterface createEvent(UserInterface user, String name, String description, String date, String local,
                                      String organization) {
        return EventUtility.createEvent(user.getCPF(), name, date, local, organization, description);
    }

    static ArrayList<EventInterface> showAllEvents() {
        ArrayList<Event> events = EventUtility.getAllEvents();
        return new ArrayList<EventInterface>(events);
    }

    static boolean deleteEvent(String CPF, String eventID) {
        boolean user = UserUtility.deleteAttendeeEvent(CPF, eventID);

        return true;
    }

    static ArrayList<EventInterface> eventByUser(String ownerCPF) {
        ArrayList<Event> eventByUser = EventUtility.getAllEventsByUser(ownerCPF);

        return new ArrayList<EventInterface>(eventByUser);
    }
//    static boolean updateEventName(String CPF, String eventID) {
//
//    }
}
