package org.upe.controllers;

import jakarta.inject.Inject;
import org.upe.controllers.interfaces.SessionControllerInterface;
import org.upe.persistence.DAO.SessionDAO;
import org.upe.persistence.DAO.SubEventDAO;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SessionController implements SessionControllerInterface {
    @Inject
    private SessionDAO sessionDAO;

    public SessionInterface create(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent) {
        return sessionDAO.create(name, date, beginHour, endHour, local, description, speaker, parentSubEvent);
    }

    public boolean delete(UUID id) {
        sessionDAO.delete(id);
        return true;
    }
}