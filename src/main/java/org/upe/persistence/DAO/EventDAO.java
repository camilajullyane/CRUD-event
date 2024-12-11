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
        // Usando o Builder para criar o Event
        Event event = Event.builder()
                .withName(name)
                .withDescription(description)
                .withBeginDate(beginDate)
                .withEndDate(endDate)
                .withLocal(local)
                .withOrganization(organization)
                .withOwner((User) user)
                .build();

        // Persistindo o evento no banco de dados
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        return event;
    }


    public void delete(UUID id) {
        try {
            entityManager.getTransaction().begin();
            Event eventToBeDeleted = (Event) findById(id);
            eventToBeDeleted.setPrivateEvent(true);
            entityManager.merge(eventToBeDeleted);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

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
