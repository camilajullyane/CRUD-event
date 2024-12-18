package org.upe.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.upe.persistence.DAO.SessionDAO;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionDAO sessionDAO;

    @Mock
    private SessionInterface mockSession;

    @Mock
    private SubEventInterface mockSubEvent;

    @Test
    void testCreateSession() {
        // Arrange
        String name = "Session Name";
        LocalDate date = LocalDate.now();
        LocalDateTime beginHour = LocalDateTime.now();
        LocalDateTime endHour = LocalDateTime.now().plusHours(1);
        String local = "Room A";
        String description = "Description of the session";
        String speaker = "Speaker Name";

        // Configura o comportamento do mock para o método create da sessionDAO
        when(sessionDAO.create(name, date, beginHour, endHour, local, description, speaker, mockSubEvent)).thenReturn(mockSession);

        // Act
        SessionInterface result = sessionController.create(name, date, beginHour, endHour, local, description, speaker, mockSubEvent);

        // Assert
        assertNotNull(result);
        verify(sessionDAO).create(name, date, beginHour, endHour, local, description, speaker, mockSubEvent);
    }

    @Test
    void testDeleteSession() {
        // Arrange
        UUID sessionId = UUID.randomUUID();
        when(mockSession.getId()).thenReturn(sessionId);

        // Act
        boolean result = sessionController.delete(sessionId);

        // Assert
        assertTrue(result);
        verify(sessionDAO).delete(sessionId);
    }
}
