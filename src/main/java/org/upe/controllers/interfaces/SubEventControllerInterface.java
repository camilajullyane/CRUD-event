package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
import java.util.UUID;

public interface SubEventControllerInterface {
    SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate beginDate, LocalDate endDate,String description);
    boolean editSubEventName(SubEventInterface subEvent, String newName);
    boolean editSubEventDate(SubEventInterface subEvent, LocalDate newDate);
    boolean editSubEventDescription(SubEventInterface subEvent, String newDescription);
    boolean addAttendeeSubEventOnList(UserInterface user, SubEventInterface subEvent);

    SubEventInterface getSubEventByID(UUID id);
    boolean deleteSubEvent(UUID id);
}