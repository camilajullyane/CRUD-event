package org.upe.persistence.DAO;

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

public class EventDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();

    public Event create(String name, String description, LocalDate beginDate, LocalDate endDate, String local, String organization, UserInterface user) {
        Event event = new Event(name, description, beginDate, endDate, local, organization, user);
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        return event;
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        Event event = entityManager.find(Event.class, id);
        if (event != null) {
            for (UserInterface attendee : event.getAttendeesList()) {
                attendee.getAttendeeOn().remove(event);
                entityManager.merge(attendee);
            }
            entityManager.remove(event);
        }
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
