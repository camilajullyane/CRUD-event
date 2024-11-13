package org.upe.controllers;

import org.upe.controllers.interfaces.EventControllerInterface;
import org.upe.controllers.interfaces.UserControllerInterface;
import org.upe.persistence.DAO.EventDAO;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.*;

public class EventController implements EventControllerInterface {
    private static final UserControllerInterface userController = new UserController();

    private static final EventDAO eventDAO = new EventDAO();


    public EventInterface createEvent(UserInterface user, String name, String description, Date date,String hour, String local,
                                      String organization) {
        return eventDAO.create(name, description, date, hour, local, organization, user);
    }

    public List<EventInterface> getAllEvents() {
        List<EventInterface> events = eventDAO.getAll();

        return new ArrayList<>(events);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        Optional<UserInterface> attendee = event.getAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(user.getCpf()))
                .findFirst();
        if(attendee.isPresent()) {
            return false;
        }
        event.getAttendeesList().add(user);
        eventDAO.update(event);
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

    public boolean updateDate(EventInterface event, Date newDate) {
        event.setDate(newDate);
        eventDAO.update(event);
        return true;
    }

    public boolean updateHour(EventInterface event, String hour) {
        event.setHour(hour);
        eventDAO.update(event);
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
