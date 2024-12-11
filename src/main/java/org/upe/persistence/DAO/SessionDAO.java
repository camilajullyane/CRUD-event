package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.Session;
import org.upe.persistence.model.SubEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger LOGGER = Logger.getLogger(SessionDAO.class.getName());
    public Session create(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEvent parentSubEvent) {
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
            LOGGER.severe(e.getMessage());
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
            LOGGER.log(Level.SEVERE, e.getMessage());
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

    public List<SessionInterface> getAllSessions() {
        try {
            List<Session> sessions = entityManager.createQuery("SELECT s FROM Session s", Session.class).getResultList();
            return new ArrayList<>(sessions);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }



}
