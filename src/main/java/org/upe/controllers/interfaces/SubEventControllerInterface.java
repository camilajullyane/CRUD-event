package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.SubEvent;

import java.util.List;

public interface SubEventControllerInterface {
    SubEventInterface createSubEvent(String parentEventID, String name, String local, String hour, String description, String speaker);
    List<SubEventInterface> showAllSubEvents();
    List<SubEvent> getMySubEventsByParentEventID(String parentEventID, String userCPF);
    List<SubEventInterface> getAllSubEventsByEvent(String parentID);
    boolean editSubEventName(String id, String newName);
    boolean editSubEventDate(String id, String newDate);
    boolean editSubEventLocal(String id, String newLocal);
    boolean editSubEventDescription(String id, String newDescription);
    boolean editSubEventSpeaker(String id, String newSpeaker);
    boolean deleteSubEvent(String id);
    boolean editSubEventHour(String id, String newHour);
}