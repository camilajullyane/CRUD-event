package org.upe.controllers;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;

import java.util.ArrayList;
import java.util.List;

public class EventController {
    private static final UserUtility userUtility = new UserUtility();

    public EventInterface createEvent(UserInterface user, String name, String description, String date, String local,
                                      String organization) {
        return EventUtility.createEvent(user.getCPF(), name, date, local, organization, description);
    }

    public List<EventInterface> getAllEvents() {
        List<Event> events = EventUtility.getAllEvents();
        return new ArrayList<>(events);
    }

    public List<EventInterface> getAllEventsByUser(String ownerCPF) {
        List<Event> eventByUser = EventUtility.getAllEventsByUser(ownerCPF);

        return new ArrayList<>(eventByUser);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
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
        userUtility.addAttendeeOnEvent(user.getCPF(), event.getId());
        return true;
    }

    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        for (String attendeeCPF : event.getAttendeesList()) {
            if (attendeeCPF.equals(user.getCPF())) {
                EventUtility.deleteAttendeeOnList(user.getCPF(), event.getId());
                userUtility.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }

    public boolean editEventName(String id, String newName) {
        EventUtility.updateEventName(id, newName);

        return true;
    }

    public boolean editEventLocal(String id, String newLocal) {
        EventUtility.updateEventLocal(id, newLocal);

        return true;
    }

    public boolean editEventDescription(String id, String newDescription) {
        EventUtility.updateEventDescription(id, newDescription);

        return true;
    }

    public boolean editEventOrganization(String id, String newOrganization) {
        EventUtility.updateEventOrganization(id, newOrganization);
        return true;
    }

    public boolean editEventDate(String id, String newDate) {
        EventUtility.updateEventDate(id, newDate);

        return true;
    }

    public boolean deleteEvent(String id, UserInterface user) {
        EventUtility.deleteEvent(id);
        userUtility.deleteOwnerOf(user.getCPF(), id);
        userUtility.deleteAllAttendeesFromEvent(id);
        return true;
    }
}
