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

    static ArrayList<EventInterface> userEventsIn(String ownerCPF) {
        ArrayList<Event> userEventsIn = EventUtility.getEventsIn(ownerCPF);

        return new ArrayList<EventInterface>(userEventsIn);
    }

    static boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(event.getId())) {
                return false;
            }
        }
        EventUtility.addAttendeeOnList(user.getCPF(), event.getId());
        UserUtility.addAttendeeOnEvent(user.getCPF(), event.getId());
        return true;
    }
//    static boolean updateEventName(String CPF, String eventID) {
//
//    }
}
