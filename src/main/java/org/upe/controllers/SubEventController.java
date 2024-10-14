package org.upe.controllers;

import org.upe.persistence.repository.interfaces.EventInterface;
import org.upe.persistence.repository.interfaces.SubEventInterface;
import org.upe.persistence.repository.model.SubEvent;
import org.upe.persistence.repository.repository.SubEventUtility;
import java.util.List;

import java.util.ArrayList;

public interface SubEventController {

    static SubEventInterface createSubEvent(EventInterface event, String name, String date, String local, String hour,
                                            String description, String speaker) {
        return (SubEventInterface) SubEventUtility.createSubEvent(event.getId(), name, date, hour, local, event.getOrganization(),description,
                speaker);
    }

    static List<SubEventInterface> showAllSubEvents() {
        List<SubEvent> subEvents = SubEventUtility.getAllSubEvents();

        return new ArrayList<>(subEvents);
    }

    static ArrayList<SubEventInterface> subEventsByEvent(String parentID) {
        List<SubEvent> subEventsByEvent = SubEventUtility.getSubEventByEvent(parentID);
        return new ArrayList<>(subEventsByEvent);
    }

    static boolean editSubEventName(String id, String newName) {
        SubEventUtility.updateSubEventName(id, newName);
        return true;
    }

    static boolean editSubEventDate(String id, String newDate) {
        SubEventUtility.updateSubEventDate(id, newDate);
        return true;
    }

    static boolean editSubEventLocal(String id, String newLocal) {
        SubEventUtility.updateSubEventLocal(id, newLocal);
        return true;
    }

    static boolean editSubEventDescription(String id, String newDescription) {
        SubEventUtility.updateSubEventDescription(id, newDescription);
        return true;
    }

    static boolean editSubEventSpeaker(String id, String newSpeaker) {
        SubEventUtility.updateSubEventSpeaker(id, newSpeaker);
        return true;
    }

    static boolean deleteSubEvent(String id) {
        SubEventUtility.deleteSubEvent(id);
        return true;
    }
}
