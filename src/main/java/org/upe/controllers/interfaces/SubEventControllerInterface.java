package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.util.UUID;

public interface SubEventControllerInterface {
    SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate date, String description, String speaker);
    boolean editSubEventName(SubEventInterface subEvent, String newName);
    boolean editSubEventDate(SubEventInterface subEvent, LocalDate newDate);
    boolean editSubEventDescription(SubEventInterface subEvent, String newDescription);
    boolean editSubEventSpeaker(SubEventInterface subEvent, String newSpeaker);

    SubEventInterface getSubEventByID(UUID id);
    boolean deleteSubEvent(UUID id);
}