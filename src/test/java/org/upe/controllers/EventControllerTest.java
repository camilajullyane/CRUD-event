package org.upe.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.upe.persistence.DAO.ArticleDAO;
import org.upe.persistence.DAO.EventDAO;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
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

//    @Test
//    void testAddAttendeeOnList() {
//        // Arrange
//        UserInterface eventOwner = mock(UserInterface.class); // Cria um mock para o dono do evento
//        when(mockEvent.getOwner()).thenReturn(eventOwner); // O dono do evento NÃO é mockUser
//        when(mockEvent.getAttendeesList()).thenReturn(Collections.emptyList()); // Lista de participantes vazia
//        doNothing().when(mockEvent).addAttendeeOnEvent(mockUser); // Métodos void
//        doNothing().when(mockUser).subscribeToEvent(mockEvent);  // Métodos void
//        when(eventDAO.update(mockEvent)).thenReturn(mockEvent); // Método com retorno
//        when(userDAO.update(mockUser)).thenReturn(mockUser);    // Método com retorno
//
//        // Act
//        boolean result = eventController.addAttendeeOnList(mockUser, mockEvent);
//
//        // Assert
//        assertTrue(result); // Verifica se o método retornou true
//        verify(mockEvent).addAttendeeOnEvent(mockUser); // Verifica se o método foi chamado
//        verify(mockUser).subscribeToEvent(mockEvent);  // Verifica se o método foi chamado
//        verify(eventDAO).update(mockEvent);            // Verifica se o método foi chamado
//        verify(userDAO).update(mockUser);              // Verifica se o método foi chamado
//    }





    @Test
    void testAddArticleOnList() {
        // Arrange
        List<EventInterface> mutableArticlesList = new ArrayList<>(); // Lista mutável criada para o teste

        // Configura o comportamento do mock para retornar essa lista
        when(mockArticle.getSubmittedIn()).thenReturn(mutableArticlesList);
        when(mockEvent.getArticles()).thenReturn(new ArrayList<>()); // Também retorna uma lista mutável
        doNothing().when(mockEvent).addArticleOnEvent(mockArticle);
        when(articleDAO.update(mockArticle)).thenReturn(mockArticle);

        // Act
        boolean result = eventController.addArticleOnList(mockArticle, mockEvent);

        // Assert
        assertTrue(result);
        verify(mockEvent).addArticleOnEvent(mockArticle); // Verifica se o método foi chamado
        verify(eventDAO).update(mockEvent); // Verifica se o evento foi atualizado
        verify(articleDAO).update(mockArticle); // Verifica se o artigo foi atualizado
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
}
