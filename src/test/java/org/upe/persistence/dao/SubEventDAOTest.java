package org.upe.persistence.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.SubEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubEventDAOTest {

    private EntityManager mockEntityManager;
    private EntityTransaction mockTransaction;
    private SubEventDAO subEventDAO;

    @BeforeEach
    void setUp() {
        mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        subEventDAO = new SubEventDAO(mockEntityManager);
    }

    @Test
    void testCreateEvent() {
        String name = "subEvent Name";
        String description = "Event Description";
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        EventInterface event = new Event();

        SubEvent subEvent = subEventDAO.create(name, description, beginDate, endDate, event);

        assertNotNull(event);
        verify(mockEntityManager).persist(subEvent);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCreateEventWithException() {
        String name = "subEvent Name";
        String description = "Event Description";
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        EventInterface event = new Event();

        doThrow(new RuntimeException("Exception during persist")).when(mockEntityManager).persist(any(SubEvent.class));

        SubEvent subEvent = subEventDAO.create(name, description, beginDate, endDate, event);

        assertNull(subEvent);
        verify(mockEntityManager).persist(any(SubEvent.class));
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testUpdateSubEvent() {
        SubEventInterface subEvent = new SubEvent();

        SubEventInterface updatedSubEvent = subEventDAO.update(subEvent);

        assertNotNull(updatedSubEvent);
        verify(mockEntityManager).merge(subEvent);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateSubEventWithException() {
        SubEventInterface subEvent = new SubEvent();

        doThrow(new RuntimeException("Exception during merge")).when(mockEntityManager).merge(any(SubEvent.class));

        SubEventInterface updatedSubEvent = subEventDAO.update(subEvent);

        assertNull(updatedSubEvent);
        verify(mockEntityManager).merge(subEvent);
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testDeleteSubEvent() {
        UUID subEventId = UUID.randomUUID();
        SubEvent subEvent = new SubEvent();
        subEvent.setId(subEventId);

        when(mockEntityManager.find(SubEvent.class, subEventId)).thenReturn(subEvent);

        subEventDAO.delete(subEventId);

        verify(mockEntityManager).find(SubEvent.class, subEventId);
        verify(mockEntityManager).merge(subEvent);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
        assertTrue(subEvent.isPrivateSubEvent());
    }

    @Test
    void testDeleteSubEventWithException() {
        UUID subEventId = UUID.randomUUID();

        when(mockEntityManager.find(SubEvent.class, subEventId)).thenThrow(new RuntimeException("Exception during find"));

        subEventDAO.delete(subEventId);

        verify(mockEntityManager).find(SubEvent.class, subEventId);
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
    }

    @Test
    void testGetAllSubEvents() {
        List<SubEvent> subEvents = new ArrayList<>();
        subEvents.add(new SubEvent());
        TypedQuery<SubEvent> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT s FROM SubEvent s", SubEvent.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(subEvents);

        List<SubEventInterface> result = subEventDAO.getAllSubEvents();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockEntityManager).createQuery("SELECT s FROM SubEvent s", SubEvent.class);
        verify(mockQuery).getResultList();
    }

    @Test
    void testGetAllSubEventsWithException() {
        TypedQuery<SubEvent> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT s FROM SubEvent s", SubEvent.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Exception during query"));

        List<SubEventInterface> result = subEventDAO.getAllSubEvents();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockEntityManager).createQuery("SELECT s FROM SubEvent s", SubEvent.class);
        verify(mockQuery).getResultList();
    }

    @Test
    void testAllSubEventsWithoutCertificate() {
        List<SubEvent> subEvents = new ArrayList<>();
        subEvents.add(new SubEvent());
        TypedQuery<SubEvent> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT s FROM SubEvent s WHERE s.endDate < CURRENT_DATE AND isCertified = FALSE", SubEvent.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(subEvents);

        List<SubEventInterface> result = subEventDAO.allSubEventsWithoutCertificate();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockEntityManager).createQuery("SELECT s FROM SubEvent s WHERE s.endDate < CURRENT_DATE AND isCertified = FALSE", SubEvent.class);
        verify(mockQuery).getResultList();
    }

    @Test
    void testAllSubEventsWithoutCertificateWithException() {
        TypedQuery<SubEvent> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT s FROM SubEvent s WHERE s.endDate < CURRENT_DATE AND isCertified = FALSE", SubEvent.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Exception during query"));

        List<SubEventInterface> result = subEventDAO.allSubEventsWithoutCertificate();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockEntityManager).createQuery("SELECT s FROM SubEvent s WHERE s.endDate < CURRENT_DATE AND isCertified = FALSE", SubEvent.class);
        verify(mockQuery).getResultList();
    }

    @Test
    void testGetById() {
        UUID subEventId = UUID.randomUUID();
        SubEvent subEvent = new SubEvent();
        subEvent.setId(subEventId);

        when(mockEntityManager.find(SubEvent.class, subEventId)).thenReturn(subEvent);

        SubEventInterface foundSubEvent = subEventDAO.getById(subEventId);

        assertNotNull(foundSubEvent);
        assertEquals(subEventId, foundSubEvent.getId());
        verify(mockEntityManager).find(SubEvent.class, subEventId);
    }

    @Test
    void testGetByIdWithException() {
        UUID subEventId = UUID.randomUUID();

        when(mockEntityManager.find(SubEvent.class, subEventId)).thenThrow(new RuntimeException("Exception during find"));

        SubEventInterface foundSubEvent = subEventDAO.getById(subEventId);

        assertNull(foundSubEvent);
        verify(mockEntityManager).find(SubEvent.class, subEventId);
    }
}