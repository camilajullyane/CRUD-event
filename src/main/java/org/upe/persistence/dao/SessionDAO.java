package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger logger = Logger.getLogger(SessionDAO.class.getName());


    public SessionInterface create(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent) {
        try {
            Session session = new Session.Builder()
                    .withName(name)
                    .withDate(date)
                    .withBeginHour(beginHour)
                    .withEndHour(endHour)
                    .withLocal(local)
                    .withDescription(description)
                    .withSpeaker(speaker)
                    .withParentSubEvent(parentSubEvent)
                    .build();
            entityManager.getTransaction().begin();
            entityManager.persist(session);
            entityManager.getTransaction().commit();
            return session;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    public SessionInterface update(SessionInterface session) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(session);
            entityManager.getTransaction().commit();
            return session;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public void delete(UUID id) {
        try {
            entityManager.getTransaction().begin();
            Session sessionToBeDeleted = (Session) getById(id);
            sessionToBeDeleted.setPrivateSession(true);
            entityManager.merge(sessionToBeDeleted);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public SessionInterface getById(UUID id) {
        return entityManager.find(Session.class, id);
    }
}
