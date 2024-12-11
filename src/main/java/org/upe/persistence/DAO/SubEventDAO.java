package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.SubEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubEventDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger LOGGER = Logger.getLogger(SubEventDAO.class.getName());

    public SubEvent create(String name, String description, LocalDate beginDate, LocalDate endDate, EventInterface parentEvent) {
        try {
            SubEvent subEvent = new SubEvent.Builder()
                    .withName(name)
                    .withDescription(description)
                    .withBeginDate(beginDate)
                    .withEndDate(endDate)
                    .withParentEvent(parentEvent)
                    .build();
    
            entityManager.getTransaction().begin();
            entityManager.persist(subEvent);
            entityManager.getTransaction().commit();
            return subEvent;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public SubEventInterface update(SubEventInterface subEvent) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(subEvent);
            entityManager.getTransaction().commit();
            return subEvent;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(getById(id));
        entityManager.getTransaction().commit();
    }

    public List<SubEventInterface> getAllSubEvents() {
        try {
            List<SubEvent> subEvents = entityManager.createQuery("SELECT s FROM SubEvent s", SubEvent.class).getResultList();
            return new ArrayList<>(subEvents);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public SubEventInterface getById(UUID id) {
        return entityManager.find(SubEvent.class, id);
    }
}
