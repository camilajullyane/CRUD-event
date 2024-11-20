package org.upe.controllers;

import org.upe.controllers.interfaces.EventControllerInterface;
import org.upe.controllers.interfaces.UserControllerInterface;
import org.upe.persistence.DAO.EventDAO;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.*;

public class EventController implements EventControllerInterface {
    private static final EventDAO eventDAO = new EventDAO();
    private static final UserDAO userDAO = new UserDAO();


    public EventInterface createEvent(UserInterface user, String name, String description, LocalDate beginDate, LocalDate endDate, String local,
                                      String organization) {
        EventInterface event = eventDAO.create(name, description, beginDate, endDate, local, organization, user);
        user.addMyEventAsOwner(event);
        return event;
    }

    public List<EventInterface> getAllEvents() {
        List<EventInterface> events = eventDAO.getAll();

        return new ArrayList<>(events);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        if (event.getOwner().getId() == user.getId()) {
            return false;
        }

        Optional<UserInterface> attendee = event.getAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(user.getCpf()))
                .findFirst();
        if(attendee.isPresent()) {
            return false;
        }

        user.subscribeToEvent(event);
        event.addAttendeeOnEvent(user);
        eventDAO.update(event);
        userDAO.update((User) user);
        return true;
    }

    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        event.getAttendeesList().remove(user);
        eventDAO.update(event);
        return true;
    }

    public boolean updateName(EventInterface event, String newName) {
        event.setName(newName);
        eventDAO.update(event);
        return true;
    }

    public boolean updateLocal(EventInterface event, String newLocal) {
        event.setLocal(newLocal);
        eventDAO.update(event);
        return true;
    }

    public boolean updateDescription(EventInterface event, String newDescription) {
        event.setDescription(newDescription);
        eventDAO.update(event);
        return true;
    }

    public boolean updateOrganization(EventInterface event, String newOrganization) {
        event.setOrganization(newOrganization);
        return true;
    }

    public boolean deleteEvent(EventInterface event, UserInterface user) {
        eventDAO.delete(event.getId());
        return true;
    }

    public EventInterface getEventById(UUID id) {
        return eventDAO.findById(id);
    }
}
