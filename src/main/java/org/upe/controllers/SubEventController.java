package org.upe.controllers;

import org.upe.controllers.interfaces.SubEventControllerInterface;
import org.upe.persistence.dao.SubEventDAO;
import org.upe.persistence.dao.UserDAO;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class SubEventController implements SubEventControllerInterface {
    public SubEventDAO subEventDAO;
    public UserDAO userDAO;

    public SubEventController(SubEventDAO subEventDAO, UserDAO userDAO) {
        this.subEventDAO = subEventDAO;
        this.userDAO = userDAO;
    }

    // falta regras de negocio
    public SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate beginDate,LocalDate endDate, String description) {
        return subEventDAO.create(name, description, beginDate, endDate,parentEvent);
    }

    public boolean addAttendeeSubEventOnList(UserInterface user, SubEventInterface subEvent) {

        Optional<UserInterface> attendee = subEvent.getSubEventAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(user.getCpf()))
                .findFirst();

        if(attendee.isPresent()) {
            return false;
        }

        user.subscribeToSubEvent(subEvent);
        subEvent.addAttendeeOnSubEvent(user);
        subEventDAO.update(subEvent);
        userDAO.update((User) user);
        return true;
    }

    public boolean removeAttendeeSubEventOnList(UserInterface user, SubEventInterface subEvent) {
        user.unsubscribeToSubEvent(subEvent);
        subEvent.removeAttendeeOnSubEvent(user);
        subEventDAO.update(subEvent);
        userDAO.update((User) user);
        return true;
    }

    public boolean editSubEventName(SubEventInterface subEvent, String newName) {
        subEvent.setName(newName);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean editSubEventBeginDate(SubEventInterface subEvent, LocalDate newDate) {
        subEvent.setBeginDate(newDate);
        subEventDAO.update(subEvent);
        return true;
    }
    public boolean editSubEventEndDate(SubEventInterface subEvent, LocalDate newDate) {
        subEvent.setEndDate(newDate);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean editSubEventDescription(SubEventInterface subEvent, String newDescription) {
        subEvent.setDescription(newDescription);
        subEventDAO.update(subEvent);
        return true;
    }

    public boolean editSubEventIsCertified(SubEventInterface subEvent) {
        subEvent.setIsCertified();
        subEventDAO.update(subEvent);
        return true;
    }


    public boolean deleteSubEvent(UUID id) {
        subEventDAO.delete(id);
        return true;
    }

    public SubEventInterface getSubEventByID(UUID id) {
        return subEventDAO.getById(id);
    }


}
