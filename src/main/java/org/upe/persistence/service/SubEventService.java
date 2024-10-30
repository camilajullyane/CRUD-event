package org.upe.persistence.service;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.SubEventUtility;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SubEventService {
    private static final SubEventUtility subEventUtility = new SubEventUtility();
    private static final EventUtility eventUtility = new EventUtility();

    public EventInterface createSubEvent(String parentEventID, String name, String hour, String local,
                                         String organization, String description, String speaker) {
        return subEventUtility.createSubEvent(parentEventID, name, hour, local, organization, description, speaker);
    }


    public boolean addSubEvent(SubEvent subEvent) {
        if (getSubEventById(subEvent.getId()) != null) {
            return false;
        }
        subEvent.setId(generateSubEventID());
        return subEventUtility.addSubEvent(subEvent);
    }

    public List<SubEvent> getAllSubEvents() {
        return subEventUtility.getAllSubEvents();
    }

    public SubEvent getSubEventById(String id) {
        return subEventUtility.getSubEventById(id);
    }

    public List<SubEvent> getAllSubEventsByEvent(String parentEventID) {
        return subEventUtility.getAllSubEventsByEvent(parentEventID);
    }

    public boolean updateSubEvent(SubEvent updatedSubEvent) {
        return subEventUtility.updateSubEvent(updatedSubEvent);
    }

    public boolean updateSubEventName(String id, String newName) {
        return subEventUtility.updateSubEventName(id, newName);
    }

    public boolean updateSubEventDate(String parentEventID, String newDate) {
        return subEventUtility.updateSubEventDate(parentEventID, newDate);
    }

    public boolean updateSubEventLocal(String id, String newLocal) {
        return subEventUtility.updateSubEventLocal(id, newLocal);
    }

    public boolean updateSubEventDescription(String id, String newDescription) {
        return subEventUtility.updateSubEventDescription(id, newDescription);
    }

    public boolean updateSubEventSpeaker(String id, String newSpeaker) {
        return subEventUtility.updateSubEventSpeaker(id, newSpeaker);
    }

    public boolean deleteSubEvent(String id) {
        return subEventUtility.deleteSubEvent(id);
    }

    public void addAttendeeOnList(UserInterface user, String subEventID) {
        subEventUtility.addAttendeeOnList(user, subEventID);
    }

    public void deleteAttendeeOnList(String userCPF, String subEventID) {
        subEventUtility.deleteAttendeeOnList(userCPF, subEventID);
    }

    public String generateSubEventID() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (getSubEventById(uuid) != null);
        return uuid;
    }

    public List<SubEvent> getMySubEventsByParentEventID(String parentEventID, String userCPF) {
        Event event = eventUtility.getEventById(parentEventID);
        if (event != null && event.getOwnerCPF().equals(userCPF)) {
            return getAllSubEventsByEvent(parentEventID);
        }
        return Collections.emptyList();
    }
}

