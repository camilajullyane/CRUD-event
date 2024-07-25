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

    static ArrayList<EventInterface> eventByUser(String ownerCPF) {
        ArrayList<Event> eventByUser = EventUtility.getAllEventsByUser(ownerCPF);

        return new ArrayList<EventInterface>(eventByUser);
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

    static boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        for(String eventID : event.getAttendeesList()) {
            if(eventID.equals(event.getId())) {
                EventUtility.deleteAttendeeOnList(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;

    }
}
