package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import java.util.Date;
import java.util.UUID;

public interface SubEventControllerInterface {
    SubEventInterface createSubEvent(EventInterface parentEvent, String name, Date date, String description, String speaker);
    boolean editSubEventName(SubEventInterface subEvent, String newName);
    boolean editSubEventDate(SubEventInterface subEvent, Date newDate);
    boolean editSubEventDescription(SubEventInterface subEvent, String newDescription);
    boolean editSubEventSpeaker(SubEventInterface subEvent, String newSpeaker);
    boolean deleteSubEvent(UUID id);
}