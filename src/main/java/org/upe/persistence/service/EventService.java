package org.upe.persistence.service;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;

import java.util.List;

public class EventService {
    private static final EventUtility eventUtility = new EventUtility();
    private static final UserUtility userUtility = new UserUtility();

    public List<Event> getAllEvents() {
        return eventUtility.getAllEvents();
    }

    public List<Event> getAllEventsByUser(String ownerCPF) {
        return eventUtility.getAllEventsByUser(ownerCPF);
    }

    public List<Event> getEventsIn(String cpf) {
        return eventUtility.getEventsIn(cpf);
    }

    public EventInterface createEvent(String userCPF, String name, String date, String local, String organization, String description) {
        return eventUtility.createEvent(userCPF, name, date, local, organization, description);
    }

    public Event getEventById(String id) {
        return eventUtility.getEventById(id);
    }

    public boolean updateEventLocal(String id, String newLocal) {
        return eventUtility.updateEventLocal(id, newLocal);
    }

    public boolean updateEventName(String id, String newName) {
        return eventUtility.updateEventName(id, newName);
    }

    public boolean updateEventDescription(String id, String newDescription) {
        return eventUtility.updateEventDescription(id, newDescription);
    }

    public boolean updateEventOrganization(String id, String newOrganization) {
        return eventUtility.updateEventOrganization(id, newOrganization);
    }

    public boolean updateEventDate(String id, String newDate) {
        return eventUtility.updateEventDate(id, newDate);
    }

    public boolean deleteEvent(String id) {
        return eventUtility.deleteEvent(id);
    }

    public boolean addAttendeeOnList(UserInterface user, String eventID) {
        return eventUtility.addAttendeeOnList(user, eventID);
    }

    public void deleteAttendeeOnList(String userCPF, String eventID) {
        eventUtility.deleteAttendeeOnList(userCPF, eventID);
    }

    public boolean addArticleOnList(String articleID, String eventID) {
        return eventUtility.addArticleOnList(articleID, eventID);
    }
}
