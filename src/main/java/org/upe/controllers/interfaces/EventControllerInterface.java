package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.List;

public interface EventControllerInterface {
    EventInterface createEvent(UserInterface user, String name, String description, String date, String local, String organization);
    List<EventInterface> getAllEvents();
    List<EventInterface> getEventsIn(String ownerCPF);
    List<EventInterface> getAllEventsByUser(String ownerCPF);
    boolean addAttendeeOnList(UserInterface user, EventInterface event);
    boolean deleteAttendeeOnList(UserInterface user, EventInterface event);
    boolean editEventName(String id, String newName);
    boolean editEventLocal(String id, String newLocal);
    boolean editEventDescription(String id, String newDescription);
    boolean editEventOrganization(String id, String newOrganization);
    boolean editEventDate(String id, String newDate);
    boolean deleteEvent(String id, UserInterface user);
}