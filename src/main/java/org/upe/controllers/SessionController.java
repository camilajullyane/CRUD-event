package org.upe.controllers;

import org.upe.persistence.DAO.SessionDAO;
import org.upe.persistence.DAO.SubEventDAO;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SessionController {
    private final SessionDAO sessionDAO = new SessionDAO();
    private final SubEventDAO subEventDAO = new SubEventDAO();

    public SessionInterface create(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent) {
        return sessionDAO.create(name, date, beginHour, endHour, local, description, speaker, parentSubEvent);
    }

    public SessionInterface update(SessionInterface session) {
        return sessionDAO.update(session);
    }

    public void delete(UUID id) {
        sessionDAO.delete(id);
    }
}
