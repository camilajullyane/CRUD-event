package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.JPAUtils.EntityManagerFactory;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.model.Event;

import java.util.Date;
import java.util.UUID;

public class SubEventDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();


    public SubEvent create(String name, String speakers, String hour, String description, Date date, Event parentEvent) {
        SubEvent subEvent = new SubEvent(name,speakers,hour, description,date,parentEvent);
        entityManager.getTransaction().begin();
        entityManager.persist(subEvent);
        entityManager.getTransaction().commit();
        return subEvent;
    }


    public SubEvent findById(UUID id) {
        return entityManager.find(SubEvent.class, id);
    }


    public SubEvent update(SubEvent subEvent) {
        entityManager.getTransaction().begin();
        entityManager.merge(subEvent);
        entityManager.getTransaction().commit();
        return subEvent;
    }


    public void delete(SubEventInterface subEvent) {
        entityManager.getTransaction().begin();
        entityManager.remove(subEvent);
        entityManager.getTransaction().commit();
    }


}
