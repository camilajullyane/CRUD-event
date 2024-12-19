package org.upe.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.upe.persistence.dao.ArticleDAO;
import org.upe.persistence.dao.EventDAO;
import org.upe.persistence.dao.UserDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventDAO eventDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private ArticleDAO articleDAO;

    @Mock
    private User mockUser;
    @Mock
    private Event mockEvent;
    @Mock
    private ArticleInterface mockArticle;
    @Mock
    private EventInterface mockEventInterface;

    @Test
    void testCreateEvent() {
        // Arrange
        String name = "Event Name";
        String description = "Description";
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        String local = "Location";
        String organization = "Organization";

        // Configurar mock para o método create do eventDAO
        when(eventDAO.create(name, description, beginDate, endDate, local, organization, mockUser)).thenReturn(mockEvent);

        // Configurar mock para o método addMyEventAsOwner
        doNothing().when(mockUser).addMyEventAsOwner(mockEvent);

        // Act
        EventInterface result = eventController.createEvent(mockUser, name, description, beginDate, endDate, local, organization);

        // Assert
        assertNotNull(result);
        verify(eventDAO).create(name, description, beginDate, endDate, local, organization, mockUser);
        verify(mockUser).addMyEventAsOwner(mockEvent);
    }


    @Test
    void testGetAllEvents() {
        when(eventDAO.getAll()).thenReturn(Collections.singletonList(mockEvent));

        List<EventInterface> result = eventController.getAllEvents();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockEvent, result.get(0));

        verify(eventDAO).getAll();
    }

    @Test
    void testAddAttendeeOnList() {
        // Arrange
        UserInterface eventOwner = mock(UserInterface.class);
        when(mockUser.getId()).thenReturn(1L);
        when(eventOwner.getId()).thenReturn(2L);
        when(mockEventInterface.getOwner()).thenReturn(eventOwner); // O dono do evento NÃO é mockUser
        when(mockEventInterface.getAttendeesList()).thenReturn(Collections.emptyList()); // Lista de participantes vazia
        doNothing().when(mockEventInterface).addAttendeeOnEvent(mockUser); // Métodos void
        doNothing().when(mockUser).subscribeToEvent(mockEventInterface);  // Métodos void// Método com retorno
        when(userDAO.update(mockUser)).thenReturn(mockUser);    // Método com retorno

        // Act
        boolean result = eventController.addAttendeeOnList(mockUser, mockEventInterface);

        // Assert
        assertTrue(result); // Verifica se o método retornou true
        verify(mockEventInterface).addAttendeeOnEvent(mockUser); // Verifica se o método foi chamado
        verify(mockUser).subscribeToEvent(mockEventInterface);  // Verifica se o método foi chamado
        verify(eventDAO).update(mockEventInterface);            // Verifica se o método foi chamado
        verify(userDAO).update(mockUser);              // Verifica se o método foi chamado
    }

    @Test
    void testAddOwnerOnHisOwnEvent() {
        when(mockEventInterface.getOwner()).thenReturn(mockUser);
        boolean result = eventController.addAttendeeOnList(mockUser, mockEventInterface);
        assertFalse(result);
    }

    @Test
    void testAddAttendeeAlreadyOnList() {
        UserInterface attendee = mock(UserInterface.class);
        when(attendee.getCpf()).thenReturn("12345678900");
        when(mockUser.getCpf()).thenReturn("12345678900");
        when(mockEventInterface.getOwner()).thenReturn(mock(UserInterface.class));
        when(mockEventInterface.getAttendeesList()).thenReturn(Collections.singletonList(attendee));

        boolean result = eventController.addAttendeeOnList(mockUser, mockEventInterface);

        assertFalse(result);
    }

    @Test
    void testAddArticleOnList() {
        List<EventInterface> mutableArticlesList = new ArrayList<>();

        when(mockArticle.getSubmittedIn()).thenReturn(mutableArticlesList);
        when(mockEvent.getArticles()).thenReturn(new ArrayList<>());
        doNothing().when(mockEvent).addArticleOnEvent(mockArticle);
        when(articleDAO.update(mockArticle)).thenReturn(mockArticle);

        boolean result = eventController.addArticleOnList(mockArticle, mockEvent);

        assertTrue(result);
        verify(mockEvent).addArticleOnEvent(mockArticle);
        verify(eventDAO).update(mockEvent);
        verify(articleDAO).update(mockArticle);
    }

    @Test
    void testGetEventById() {
        UUID eventId = UUID.randomUUID();
        when(eventDAO.findById(eventId)).thenReturn(mockEvent);

        EventInterface result = eventController.getEventById(eventId);

        assertNotNull(result);
        assertEquals(mockEvent, result);
        verify(eventDAO).findById(eventId);
    }

    @Test
    void testGetEventWithInvalidId() {
        UUID eventId = UUID.randomUUID();
        when(eventDAO.findById(eventId)).thenReturn(null);

        EventInterface result = eventController.getEventById(eventId);

        assertNull(result);
        verify(eventDAO).findById(eventId);
    }

    @Test
    void testDeleteAttendeeOnList() {
        doNothing().when(mockEvent).removeAttendeeOnEvent(mockUser);
        doNothing().when(mockUser).unsubscribeToEvent(mockEvent);

        boolean result = eventController.deleteAttendeeOnList(mockUser, mockEvent);

        assertTrue(result);
        verify(mockEvent).removeAttendeeOnEvent(mockUser);
        verify(mockUser).unsubscribeToEvent(mockEvent);
        verify(eventDAO).update(mockEvent);
        verify(userDAO).update(any(User.class));
    }

    @Test
    void testDeleteEvent() {
        UUID eventId = UUID.randomUUID();
        when(mockEvent.getId()).thenReturn(eventId);
        when(eventDAO.delete(eventId)).thenReturn(true);

        boolean result = eventController.deleteEvent(mockEvent, mockUser);

        assertTrue(result);
        verify(eventDAO).delete(eventId);
    }

    @Test
    void testUpdateName() {
        String newName = "Updated Event Name";
        doNothing().when(mockEvent).setName(newName);

        boolean result = eventController.updateName(mockEvent, newName);

        assertTrue(result);
        verify(mockEvent).setName(newName);
        verify(eventDAO).update(mockEvent);
    }

    @Test
    void testUpdateLocal() {
        String newLocal = "Updated Location";
        doNothing().when(mockEvent).setLocal(newLocal);

        boolean result = eventController.updateLocal(mockEvent, newLocal);

        assertTrue(result);
        verify(mockEvent).setLocal(newLocal);
        verify(eventDAO).update(mockEvent);
    }

    @Test
    void testUpdateDescription() {
        String newDescription = "Updated Description";
        doNothing().when(mockEvent).setDescription(newDescription);

        boolean result = eventController.updateDescription(mockEvent, newDescription);

        assertTrue(result);
        verify(mockEvent).setDescription(newDescription);
        verify(eventDAO).update(mockEvent);
    }

    @Test
    void testUpdateOrganization() {
        String newOrganization = "Updated Organization";
        doNothing().when(mockEvent).setOrganization(newOrganization);

        boolean result = eventController.updateOrganization(mockEvent, newOrganization);

        assertTrue(result);
        verify(mockEvent).setOrganization(newOrganization);
        verify(eventDAO).update(mockEvent);
    }
}
