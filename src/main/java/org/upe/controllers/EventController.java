package org.upe.controllers;

import org.upe.controllers.interfaces.EventControllerInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.service.EventService;
import org.upe.persistence.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class EventController implements EventControllerInterface {
    private static final UserService userService = new UserService();
    private static final EventService eventService = new EventService();

    public EventInterface createEvent(UserInterface user, String name, String description, String date, String local,
                                      String organization) {
        return eventService.createEvent(user.getCPF(), name, date, local, organization, description);
    }

    public List<EventInterface> getAllEvents() {
        List<Event> events = eventService.getAllEvents();

        return new ArrayList<>(events);
    }

    public List<EventInterface> getEventsIn(String ownerCPF) {
        List<Event> events = eventService.getEventsIn(ownerCPF);

        return new ArrayList<>(events);
    }

    public List<EventInterface> getAllEventsByUser(String ownerCPF) {
        List<Event> eventByUser = eventService.getAllEventsByUser(ownerCPF);

        return new ArrayList<>(eventByUser);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        return eventService.addAttendeeOnList(user, event.getId()) && userService.addAttendeeOnEvent(user, event.getId());
    }

    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        for (String attendeeCPF : event.getAttendeesList()) {
            if (attendeeCPF.equals(user.getCPF())) {
                eventService.deleteAttendeeOnList(user.getCPF(), event.getId());
                userService.deleteAttendeeEvent(user.getCPF(), event.getId());
                return true;
            }
        }
        return false;
    }

    public boolean editEventName(String id, String newName) {
        eventService.updateEventName(id, newName);

        return true;
    }

    public boolean editEventLocal(String id, String newLocal) {
        eventService.updateEventLocal(id, newLocal);

        return true;
    }

    public boolean editEventDescription(String id, String newDescription) {
        eventService.updateEventDescription(id, newDescription);

        return true;
    }

    public boolean editEventOrganization(String id, String newOrganization) {
        eventService.updateEventOrganization(id, newOrganization);
        return true;
    }

    public boolean editEventDate(String id, String newDate) {
        eventService.updateEventDate(id, newDate);

        return true;
    }

    public boolean deleteEvent(String id, UserInterface user) {
        eventService.deleteEvent(id);
        userService.deleteOwnerOf(user.getCPF(), id);
        userService.deleteAllAttendeesFromEvent(id);
        return true;
    }
}
