package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class EventDAO {
    private final EntityManager entityManager;
    private final Logger logger = Logger.getLogger(EventDAO.class.getName());

    public EventDAO() {
        entityManager = EntityManagerFactory.getEntityManager();
    }

    public EventDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Event create(String name, String description, LocalDate beginDate, LocalDate endDate, String local, String organization, UserInterface user) {
        try {
            Event event = Event.builder()
                    .withName(name)
                    .withDescription(description)
                    .withBeginDate(beginDate)
                    .withEndDate(endDate)
                    .withLocal(local)
                    .withOrganization(organization)
                    .withOwner((User) user)
                    .build();

            entityManager.getTransaction().begin();
            entityManager.persist(event);
            entityManager.getTransaction().commit();
            return event;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }


    public boolean delete(UUID id) {
        try {
            entityManager.getTransaction().begin();
            Event eventToBeDeleted = (Event) findById(id);
            eventToBeDeleted.setPrivateEvent(true);
            entityManager.merge(eventToBeDeleted);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }

    }

    public EventInterface findById(UUID id) {
        try {
            return entityManager.find(Event.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public void update(EventInterface event) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(event);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    public List<EventInterface> getAll() {
        try {
            List<Event> events = entityManager.createQuery("SELECT e FROM Event e", Event.class).getResultList();
            return new ArrayList<>(events);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new ArrayList<>();
        }
    }
}
