package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EventControllerInterface {
    EventInterface createEvent(UserInterface user, String name, String description, LocalDate beginDate, LocalDate endDate,String local, String organization);
    List<EventInterface> getAllEvents();
    boolean addAttendeeOnList(UserInterface user, EventInterface event);
    boolean deleteAttendeeOnList(UserInterface user, EventInterface event);
    boolean deleteEvent(EventInterface event, UserInterface user);
    EventInterface getEventById(UUID id);

    boolean updateName(EventInterface event, String name);
    boolean updateLocal(EventInterface event, String local);
    boolean updateDescription(EventInterface event, String description);
    boolean updateOrganization(EventInterface event, String organization);
    boolean updateDate(EventInterface event, LocalDate newDate);
    boolean updateHour(EventInterface event, String Hour);
}