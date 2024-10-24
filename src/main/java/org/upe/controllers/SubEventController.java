package org.upe.controllers;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.SubEventUtility;
import java.util.List;

import java.util.ArrayList;

public class SubEventController {
    private static final EventUtility eventUtility = new EventUtility();
    private static final SubEventUtility subEventUtility = new SubEventUtility();

    public static SubEventInterface createSubEvent(String parentEventID, String name, String local, String hour,
                                                   String description, String speaker) {
        return (SubEventInterface) subEventUtility.createSubEvent(parentEventID, name, hour, local,
                eventUtility.getEventById(parentEventID).getOrganization(),description, speaker);
    }

    static List<SubEventInterface> showAllSubEvents() {
        List<SubEvent> subEvents = subEventUtility.getAllSubEvents();

        return new ArrayList<>(subEvents);
    }

    static ArrayList<SubEventInterface> subEventsByEvent(String parentID) {
        List<SubEvent> subEventsByEvent = subEventUtility.getSubEventByEvent(parentID);
        return new ArrayList<>(subEventsByEvent);
    }

    static boolean editSubEventName(String id, String newName) {
        subEventUtility.updateSubEventName(id, newName);
        return true;
    }

    static boolean editSubEventDate(String id, String newDate) {
        subEventUtility.updateSubEventDate(id, newDate);
        return true;
    }

    static boolean editSubEventLocal(String id, String newLocal) {
        subEventUtility.updateSubEventLocal(id, newLocal);
        return true;
    }

    static boolean editSubEventDescription(String id, String newDescription) {
        subEventUtility.updateSubEventDescription(id, newDescription);
        return true;
    }

    static boolean editSubEventSpeaker(String id, String newSpeaker) {
        subEventUtility.updateSubEventSpeaker(id, newSpeaker);
        return true;
    }

    static boolean deleteSubEvent(String id) {
        subEventUtility.deleteSubEvent(id);
        return true;
    }
}
