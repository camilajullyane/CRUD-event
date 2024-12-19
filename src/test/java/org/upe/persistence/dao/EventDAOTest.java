package org.upe.persistence.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventDAOTest {

    private EntityManager mockEntityManager;
    private EntityTransaction mockTransaction;
    private EventDAO eventDAO;

    @BeforeEach
    void setUp() {
        mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        eventDAO = new EventDAO(mockEntityManager);
    }

    @Test
    void testCreateEvent() {
        String name = "Event Name";
        String description = "Event Description";
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        String local = "Event Location";
        String organization = "Event Organization";
        UserInterface user = new User();

        Event event = eventDAO.create(name, description, beginDate, endDate, local, organization, user);

        assertNotNull(event);
        verify(mockEntityManager).persist(event);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCreateEventWithException() {
        String name = "Event Name";
        String description = "Event Description";
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        String local = "Event Location";
        String organization = "Event Organization";
        UserInterface user = new User();

        doThrow(new RuntimeException("Exception during persist")).when(mockEntityManager).persist(any(Event.class));

        Event event = eventDAO.create(name, description, beginDate, endDate, local, organization, user);

        assertNull(event);
        verify(mockEntityManager).persist(any(Event.class));
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testDeleteEvent() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        when(mockEntityManager.find(Event.class, eventId)).thenReturn(event);

        eventDAO.delete(eventId);

        verify(mockEntityManager).find(Event.class, eventId);
        verify(mockEntityManager).merge(event);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
        assertTrue(event.isPrivateEvent());
    }

    @Test
    void testDeleteEventWithException() {
        UUID eventId = UUID.randomUUID();

        when(mockEntityManager.find(Event.class, eventId)).thenThrow(new RuntimeException("Exception during find"));

        assertFalse(eventDAO.delete(eventId));

        verify(mockEntityManager).find(Event.class, eventId);
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
    }

    @Test
    void testFindById() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        when(mockEntityManager.find(Event.class, eventId)).thenReturn(event);

        EventInterface foundEvent = eventDAO.findById(eventId);

        assertNotNull(foundEvent);
        assertEquals(eventId, foundEvent.getId());
        verify(mockEntityManager).find(Event.class, eventId);
    }

    @Test
    void testFindByIdWithException() {
        UUID eventId = UUID.randomUUID();

        when(mockEntityManager.find(Event.class, eventId)).thenThrow(new RuntimeException("Exception during find"));

        EventInterface foundEvent = eventDAO.findById(eventId);

        assertNull(foundEvent);
        verify(mockEntityManager).find(Event.class, eventId);
    }

    @Test
    void testUpdateEvent() {
        EventInterface event = new Event();
        event.setLocal("Old Location");

        eventDAO.update(event);

        verify(mockEntityManager).merge(event);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateEventWithException() {
        EventInterface event = new Event();
        event.setLocal("Old Location");

        doThrow(new RuntimeException("Exception during merge")).when(mockEntityManager).merge(any(Event.class));

        eventDAO.update(event);

        verify(mockEntityManager).merge(event);
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        TypedQuery<Event> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT e FROM Event e", Event.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(events);

        List<EventInterface> result = eventDAO.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockEntityManager).createQuery("SELECT e FROM Event e", Event.class);
        verify(mockQuery).getResultList();
    }

    @Test
    void testGetAllEventsWithException() {
        TypedQuery<Event> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT e FROM Event e", Event.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Exception during query"));

        List<EventInterface> result = eventDAO.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockEntityManager).createQuery("SELECT e FROM Event e", Event.class);
        verify(mockQuery).getResultList();
    }
}