package org.upe.controllers;

import org.upe.controllers.interfaces.SubEventControllerInterface;
import org.upe.persistence.DAO.SubEventDAO;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.util.UUID;

public class SubEventController implements SubEventControllerInterface {
    public static final SubEventDAO subEventDAO = new SubEventDAO();

    // falta regras de negocio
    public SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate date, String description, String speaker) {
        return subEventDAO.create(name, speaker, description, date, parentEvent);
    }

    public boolean editSubEventName(SubEventInterface subEvent, String newName) {
        subEvent.setName(newName);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean editSubEventDate(SubEventInterface subEvent, LocalDate newDate) {
        subEvent.setDate(newDate);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean editSubEventDescription(SubEventInterface subEvent, String newDescription) {
        subEvent.setDescription(newDescription);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean editSubEventSpeaker(SubEventInterface subEvent, String newSpeaker) {
        subEvent.setSpeakers(newSpeaker);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean deleteSubEvent(UUID id) {
        subEventDAO.delete(id);
        return true;
    }
}
