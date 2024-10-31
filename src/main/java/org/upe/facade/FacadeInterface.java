package org.upe.facade;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.SubEvent;
import java.util.List;

public interface FacadeInterface {
    // AuthController methods
    UserInterface loginUser(String cpf, String password);
    UserInterface signUpUser(String name, String cpf, String email, String password);

    // EventController methods
    EventInterface createEvent(UserInterface user, String name, String description, String date, String local, String organization);
    List<EventInterface> getAllEvents();
    List<EventInterface> getEventsIn(String ownerCPF);
    List<EventInterface> getAllEventsByUser(String ownerCPF);
    EventInterface getEventByID(String id);
    boolean addAttendeeOnList(UserInterface user, EventInterface event);
    boolean deleteAttendeeOnList(UserInterface user, EventInterface event);
    boolean editEventName(String id, String newName);
    boolean editEventLocal(String id, String newLocal);
    boolean editEventDescription(String id, String newDescription);
    boolean editEventOrganization(String id, String newOrganization);
    boolean editEventDate(String id, String newDate);
    boolean deleteEvent(String id, UserInterface user);

    // SubEventController methods
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

    // UserController methods
    boolean deleteAttendeeEvent(String userCPF, String eventID);
    UserInterface getUserByCPF(String cpf);
    List<EventInterface> userEventsIn(String ownerCPF);
    boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event);
    boolean changeEmail(String userEmail, String newEmail);
    boolean changePassword(String cpf, String currentPassword, String newPassword);
}