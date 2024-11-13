package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.JPAUtils.EntityManagerFactory;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EventDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();

    public Event create(String name, String description, Date date,String hour, String local, String organization, UserInterface user) {
        Event event = new Event(name, description, date, hour, local, organization, user);
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        return event;
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Event.class, id));
        entityManager.getTransaction().commit();
    }

    public EventInterface findById(UUID id) {
        return entityManager.find(Event.class, id);
    }

    public void update(EventInterface event) {
        entityManager.getTransaction().begin();
        entityManager.merge(event);
        entityManager.getTransaction().commit();

    }

    public List<EventInterface> getAll() {
        List<Event> events = entityManager.createQuery("SELECT e FROM Event e", Event.class).getResultList();
        return new ArrayList<>(events);
    }
}
