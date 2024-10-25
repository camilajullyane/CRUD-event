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


    public SubEventInterface createSubEvent(EventInterface event, String name, String date, String local, String hour,
                                            String description, String speaker) {
        return (SubEventInterface) subEventUtility.createSubEvent(event.getId(), name, date, hour, local, event.getOrganization(),description,
                speaker);

    }

    public List<SubEventInterface> showAllSubEvents() {
        List<SubEvent> subEvents = subEventUtility.getAllSubEvents();

        return new ArrayList<>(subEvents);
    }

    public ArrayList<SubEventInterface> subEventsByEvent(String parentID) {
        List<SubEvent> subEventsByEvent = subEventUtility.getSubEventByEvent(parentID);
        return new ArrayList<>(subEventsByEvent);
    }

    public boolean editSubEventName(String id, String newName) {
        subEventUtility.updateSubEventName(id, newName);
        return true;
    }

    public boolean editSubEventDate(String id, String newDate) {
        subEventUtility.updateSubEventDate(id, newDate);
        return true;
    }

    public boolean editSubEventLocal(String id, String newLocal) {
        subEventUtility.updateSubEventLocal(id, newLocal);
        return true;
    }

    public boolean editSubEventDescription(String id, String newDescription) {
        subEventUtility.updateSubEventDescription(id, newDescription);
        return true;
    }

    public boolean editSubEventSpeaker(String id, String newSpeaker) {
        subEventUtility.updateSubEventSpeaker(id, newSpeaker);
        return true;
    }

    public boolean deleteSubEvent(String id) {
        subEventUtility.deleteSubEvent(id);
        return true;
    }
}
