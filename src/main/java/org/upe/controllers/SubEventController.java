package org.upe.controllers;

import org.upe.controllers.interfaces.SubEventControllerInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.oldModel.SubEvent;

import java.util.List;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.SubEventUtility;
import java.util.ArrayList;

public class SubEventController implements SubEventControllerInterface {
    private static final EventUtility eventUtiliy = new EventUtility();
    private static final SubEventUtility subEventUtility = new SubEventUtility();

    public SubEventInterface createSubEvent(String parentEventID, String name, String local, String hour, String description, String speaker) {

        return (SubEventInterface) subEventUtility.createSubEvent(parentEventID, name, hour, local,
                eventUtiliy.getEventById(parentEventID).getOrganization(),description, speaker);
    }

    public List<SubEventInterface> showAllSubEvents() {
        List<SubEvent> subEvents = subEventUtility.getAllSubEvents();
        return new ArrayList<>(subEvents);
    }


    public List<SubEvent> getMySubEventsByParentEventID(String parentEventID, String userCPF) {
        List<SubEvent> subEvents = subEventUtility.getMySubEventsByParentEventID(parentEventID,userCPF);
        return new ArrayList<>(subEvents);
    }

    public ArrayList<SubEventInterface> getAllSubEventsByEvent(String parentID) {
        List<SubEvent> subEventsByEvent = subEventUtility.getAllSubEventsByEvent(parentID);

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

    public boolean editSubEventHour(String id, String newHour) {
        subEventUtility.updateSubEventHour(id, newHour);
        return true;
    }

    public boolean deleteSubEvent(String id) {
        subEventUtility.deleteSubEvent(id);
        return true;
    }

    public void addAttendeeOnList(UserInterface user, String subEventID) {
        subEventUtility.addAttendeeOnList(user, subEventID);
    }

    public void deleteAttendeeOnList(String userCPF, String subEventID) {
        subEventUtility.deleteAttendeeOnList(userCPF, subEventID);
    }
}
