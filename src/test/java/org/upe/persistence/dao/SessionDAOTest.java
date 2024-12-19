package org.upe.persistence.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.Session;
import org.upe.persistence.model.SubEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionDAOTest {

    private EntityManager mockEntityManager;
    private EntityTransaction mockTransaction;
    private SessionDAO sessionDAO;

    @BeforeEach
    void setUp() {
        mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        sessionDAO = new SessionDAO(mockEntityManager);
    }

    @Test
    void testCreateSession() {
        String name = "session Name";
        String description = "Event Description";
        LocalDate date = LocalDate.now();
        LocalDateTime beginHour = LocalDateTime.now();
        LocalDateTime endHour = LocalDateTime.now().plusHours(1);
        String local = "Local";
        String speaker = "Speaker";
        SubEventInterface subEvent = new SubEvent();

        SessionInterface session = sessionDAO.create(name, date, beginHour, endHour, local, description, speaker, subEvent);

        assertNotNull(session);
        verify(mockEntityManager).persist(session);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCreateSessionWithException() {
        String name = "Session Name";
        LocalDate date = LocalDate.now();
        LocalDateTime beginHour = LocalDateTime.now();
        LocalDateTime endHour = LocalDateTime.now().plusHours(1);
        String local = "Local";
        String description = "Description";
        String speaker = "Speaker";
        SubEventInterface parentSubEvent = new SubEvent();

        doThrow(new RuntimeException("Exception during persist")).when(mockEntityManager).persist(any(Session.class));

        SessionInterface createdSession = sessionDAO.create(name, date, beginHour, endHour, local, description, speaker, parentSubEvent);

        assertNull(createdSession);
        verify(mockEntityManager).persist(any(Session.class));
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testUpdateSession() {
        SessionInterface session = new Session();

        SessionInterface updatedSession = sessionDAO.update(session);

        assertNotNull(updatedSession);
        verify(mockEntityManager).merge(session);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateSessionWithException() {
        SessionInterface session = new Session();

        doThrow(new RuntimeException("Exception during merge")).when(mockEntityManager).merge(session);

        SessionInterface updatedSession = sessionDAO.update(session);

        assertNull(updatedSession);
        verify(mockEntityManager).merge(session);
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testDeleteSession() {
        UUID sessionId = UUID.randomUUID();
        Session session = new Session();
        session.setId(sessionId);

        when(mockEntityManager.find(Session.class, sessionId)).thenReturn(session);

        sessionDAO.delete(sessionId);

        assertTrue(session.isPrivateSession());
        verify(mockEntityManager).find(Session.class, sessionId);
        verify(mockEntityManager).merge(session);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testDeleteSessionWithException() {
        UUID sessionId = UUID.randomUUID();

        when(mockEntityManager.find(Session.class, sessionId)).thenThrow(new RuntimeException("Exception during find"));

        assertFalse(sessionDAO.delete(sessionId));

        verify(mockEntityManager).find(Session.class, sessionId);
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
    }

    @Test
    void testGetById() {
        UUID sessionId = UUID.randomUUID();
        Session session = new Session();
        session.setId(sessionId);

        when(mockEntityManager.find(Session.class, sessionId)).thenReturn(session);

        SessionInterface foundSession = sessionDAO.getById(sessionId);

        assertNotNull(foundSession);
        assertEquals(sessionId, foundSession.getId());
        verify(mockEntityManager).find(Session.class, sessionId);
    }

    @Test
    void testGetByIdWithException() {
        UUID sessionId = UUID.randomUUID();

        when(mockEntityManager.find(Session.class, sessionId)).thenThrow(new RuntimeException("Exception during find"));

        SessionInterface foundSession = sessionDAO.getById(sessionId);

        assertNull(foundSession);
        verify(mockEntityManager).find(Session.class, sessionId);
    }
}