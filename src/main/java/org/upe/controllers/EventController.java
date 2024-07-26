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
        for (String ownerOf : user.getOwnerOf()) {
            if (ownerOf.equals(event.getId())) {
                return false;
            }
        }

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
        for (String attendeeCPF : event.getAttendeesList()) {
            if (attendeeCPF.equals(user.getCPF())) {
                EventUtility.deleteAttendeeOnList(user.getCPF(), event.getId());
                UserUtility.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }

    static boolean editEventName(String id, String newName) {
        EventUtility.updateEventName(id, newName);

        return true;
    }

    static boolean editEventLocal(String id, String newLocal) {
        EventUtility.updateEventLocal(id, newLocal);

        return true;
    }

    static boolean editEventDescription(String id, String newDescription) {
        EventUtility.updateEventDescription(id, newDescription);

        return true;
    }

    static boolean editEventOrganization(String id, String newOrganization) {
        EventUtility.updateEventOrganization(id, newOrganization);
        return true;
    }

    static boolean editEventDate(String id, String newDate) {
        EventUtility.updateEventDate(id, newDate);

        return true;
    }

    static boolean deleteEvent(String id, UserInterface user) {
        EventUtility.deleteEvent(id);
        UserUtility.deleteOwnerOf(user.getCPF(), id);
        UserUtility.deleteAllAttendeesFromEvent(id);
        return true;
    }
}
