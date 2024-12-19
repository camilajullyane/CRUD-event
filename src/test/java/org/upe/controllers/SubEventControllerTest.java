package org.upe.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.upe.persistence.dao.SubEventDAO;
import org.upe.persistence.dao.UserDAO;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubEventControllerTest {

    @InjectMocks
    private SubEventController subEventController;

    @Mock
    private SubEventDAO subEventDAOMock;

    @Mock
    private UserDAO userDAOMock;

    @Mock
    private SubEvent subEventMock;

    @Mock
    private User userMock;

    @Test
    void testCreateSubEvent() {
        // Arrange
        String name = "SubEvent 1";
        String description = "Description of SubEvent 1";
        LocalDate beginDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(subEventDAOMock.create(name, description, beginDate, endDate, null)).thenReturn(subEventMock);

        // Act
        SubEventInterface result = subEventController.createSubEvent(null, name, beginDate, endDate, description);

        // Assert
        assertNotNull(result);
        verify(subEventDAOMock).create(name, description, beginDate, endDate, null);
    }

    @Test
    void testAddAttendeeSubEventOnList() {
        // Arrange
        lenient().when(subEventMock.getSubEventAttendeesList()).thenReturn(new ArrayList<>());  // Com lenient

        // Act
        boolean result = subEventController.addAttendeeSubEventOnList(userMock, subEventMock);

        // Assert
        assertTrue(result);
        verify(subEventMock).addAttendeeOnSubEvent(userMock);
        verify(subEventDAOMock).update(subEventMock);
        verify(userDAOMock).update(userMock);
    }


    @Test
    void testAddAttendeeSubEventOnList_AlreadyAttendee() {
        // Arrange
        when(subEventMock.getSubEventAttendeesList()).thenReturn(Collections.singletonList(userMock));
        when(userMock.getCpf()).thenReturn("12345678901");

        // Act
        boolean result = subEventController.addAttendeeSubEventOnList(userMock, subEventMock);

        // Assert
        assertFalse(result);
        verify(subEventMock, never()).addAttendeeOnSubEvent(userMock);
        verify(subEventDAOMock, never()).update(subEventMock);
        verify(userDAOMock, never()).update(any(User.class));
    }

    @Test
    void testRemoveAttendeeSubEventOnList() {
        // Arrange
        lenient().when(subEventMock.getSubEventAttendeesList()).thenReturn(Collections.singletonList(userMock));

        // Act
        boolean result = subEventController.removeAttendeeSubEventOnList(userMock, subEventMock);

        // Assert
        assertTrue(result);
        verify(subEventMock).removeAttendeeOnSubEvent(userMock);
        verify(subEventDAOMock).update(subEventMock);
        verify(userDAOMock).update(any(User.class));
    }



    @Test
    void testEditSubEventName() {
        // Arrange
        String newName = "New SubEvent Name";

        // Act
        boolean result = subEventController.editSubEventName(subEventMock, newName);

        // Assert
        assertTrue(result);
        verify(subEventMock).setName(newName);
        verify(subEventDAOMock).update(subEventMock);
    }

    @Test
    void testEditSubEventDate() {
        // Arrange
        LocalDate newDate = LocalDate.now().plusDays(2);

        // Act
        boolean result = subEventController.editSubEventDate(subEventMock, newDate);

        // Assert
        assertTrue(result);
        verify(subEventMock).setBeginDate(newDate);
        verify(subEventDAOMock).update(subEventMock);
    }

    @Test
    void testEditSubEventDescription() {
        // Arrange
        String newDescription = "New Description for SubEvent";

        // Act
        boolean result = subEventController.editSubEventDescription(subEventMock, newDescription);

        // Assert
        assertTrue(result);
        verify(subEventMock).setDescription(newDescription);
        verify(subEventDAOMock).update(subEventMock);
    }

    @Test
    void testDeleteSubEvent() {
        // Arrange
        UUID subEventId = UUID.randomUUID();

        // Act
        boolean result = subEventController.deleteSubEvent(subEventId);

        // Assert
        assertTrue(result);
        verify(subEventDAOMock).delete(subEventId);
    }

    @Test
    void testGetSubEventByID() {
        // Arrange
        UUID subEventId = UUID.randomUUID();
        when(subEventDAOMock.getById(subEventId)).thenReturn(subEventMock);

        // Act
        SubEventInterface result = subEventController.getSubEventByID(subEventId);

        // Assert
        assertNotNull(result);
        verify(subEventDAOMock).getById(subEventId);
    }
}
