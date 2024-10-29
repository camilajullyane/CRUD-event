package org.upe.controllers;

import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.repository.SubEventUtility;
import java.util.List;
import org.upe.persistence.service.EventService;
import org.upe.persistence.service.SubEventService;

import java.util.ArrayList;

public class SubEventController {
    private static final EventService eventService = new EventService();
    private static final SubEventService subEventService = new SubEventService();

    public SubEventInterface createSubEvent(String parentEventID, String name, String local, String hour, String description, String speaker) {

        return (SubEventInterface) subEventService.createSubEvent(parentEventID, name, hour, local,
                eventService.getEventById(parentEventID).getOrganization(),description, speaker);
    }

    public List<SubEventInterface> showAllSubEvents() {
        List<SubEvent> subEvents = subEventService.getAllSubEvents();
        return new ArrayList<>(subEvents);
    }

    public List<SubEvent> getMySubEventsByParentEventID(String parentEventID, String userCPF) {
        List<SubEvent> subEvents = subEventService.getMySubEventsByParentEventID(parentEventID,userCPF);
        return new ArrayList<>(subEvents);
    }

    public static ArrayList<SubEventInterface> getAllSubEventsByEvent(String parentID) {
        List<SubEvent> subEventsByEvent = subEventService.getAllSubEventsByEvent(parentID);
        return new ArrayList<>(subEventsByEvent);
    }

    public boolean editSubEventName(String id, String newName) {
        subEventService.updateSubEventName(id, newName);
        return true;
    }

    public boolean editSubEventDate(String id, String newDate) {
        subEventService.updateSubEventDate(id, newDate);
        return true;
    }

    static boolean editSubEventLocal(String id, String newLocal) {
        subEventService.updateSubEventLocal(id, newLocal);
        return true;
    }

    static boolean editSubEventDescription(String id, String newDescription) {
        subEventService.updateSubEventDescription(id, newDescription);
        return true;
    }

    static boolean editSubEventSpeaker(String id, String newSpeaker) {
        subEventService.updateSubEventSpeaker(id, newSpeaker);
        return true;
    }

    static boolean deleteSubEvent(String id) {
        subEventService.deleteSubEvent(id);
        return true;
    }
}
