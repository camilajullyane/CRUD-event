package org.upe.controllers;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;

import java.util.ArrayList;
import java.util.List;

public class EventController {
    private static final UserUtility userUtility = new UserUtility();
    private static final EventUtility eventUtility = new EventUtility();

    public EventInterface createEvent(UserInterface user, String name, String description, String date, String local,
                                      String organization) {
        return eventUtility.createEvent(user, name, date, local, organization, description);
    }

    public List<EventInterface> getAllEvents() {
        List<Event> events = eventUtility.getAllEvents();
        return new ArrayList<>(events);
    }

    public List<EventInterface> getAllEventsByUser(String ownerCPF) {
        List<Event> eventByUser = eventUtility.getAllEventsByUser(ownerCPF);

        return new ArrayList<>(eventByUser);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        return eventUtility.addAttendeeOnList(user, event.getId()) && userUtility.addAttendeeOnEvent(user, event.getId());
    }

    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        for (String attendeeCPF : event.getAttendeesList()) {
            if (attendeeCPF.equals(user.getCPF())) {
                eventUtility.deleteAttendeeOnList(user.getCPF(), event.getId());
                userUtility.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }

    public boolean editEventName(String id, String newName) {
        eventUtility.updateEventName(id, newName);

        return true;
    }

    public boolean editEventLocal(String id, String newLocal) {
        eventUtility.updateEventLocal(id, newLocal);

        return true;
    }

    public boolean editEventDescription(String id, String newDescription) {
        eventUtility.updateEventDescription(id, newDescription);

        return true;
    }

    public boolean editEventOrganization(String id, String newOrganization) {
        eventUtility.updateEventOrganization(id, newOrganization);
        return true;
    }

    public boolean editEventDate(String id, String newDate) {
        eventUtility.updateEventDate(id, newDate);

        return true;
    }

    public boolean deleteEvent(String id, UserInterface user) {
        eventUtility.deleteEvent(id);
        userUtility.deleteOwnerOf(user.getCPF(), id);
        userUtility.deleteAllAttendeesFromEvent(id);
        return true;
    }
}
