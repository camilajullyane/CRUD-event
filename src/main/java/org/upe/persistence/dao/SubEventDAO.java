package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    private final Logger logger = Logger.getLogger(SubEventDAO.class.getName());

    public SubEvent create(String name, String description, LocalDate beginDate, LocalDate endDate, EventInterface parentEvent) {
        try {
            SubEvent subEvent = SubEvent.builder()
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
            logger.log(Level.SEVERE, e.getMessage());
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
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public void delete(UUID id) {
        try {
            entityManager.getTransaction().begin();
            SubEvent subEventToBeDeleted = (SubEvent) getById(id);
            subEventToBeDeleted.setPrivateSubEvent(true);
            entityManager.merge(subEventToBeDeleted);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public List<SubEventInterface> getAllSubEvents() {
        try {
            List<SubEvent> subEvents = entityManager.createQuery("SELECT s FROM SubEvent s", SubEvent.class).getResultList();
            return new ArrayList<>(subEvents);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<SubEventInterface> allSubEventsWithoutCertificate() {
        try {
            String jpql = "SELECT s FROM SubEvent s WHERE s.endDate < CURRENT_DATE AND isCertified = FALSE";
            TypedQuery<SubEvent> query = entityManager.createQuery(jpql, SubEvent.class);
            return new ArrayList<>(query.getResultList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return new ArrayList<>();
        }
    }

    public SubEventInterface getById(UUID id) {
        return entityManager.find(SubEvent.class, id);
    }
}
