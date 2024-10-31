package org.upe.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private FacadeInterface facade;
    private UserInterface testUser;
    private UserInterface testUser2;
    private EventInterface testEvent;

    @BeforeEach
    public void setUp() throws IOException {
        UserUtility.setCsvFilePath("DB/teste/test_user.csv");
        EventUtility.setCsvFilePath("DB/teste/test_event.csv");
        facade = new Facade();
        try (FileWriter writer = new FileWriter("DB/teste/test_user.csv")) {
            writer.write("name,email,cpf,password,attendeeOn,ownerOf,articleID\n");
            writer.write("Test User,test@example.com,11111111111,111111,,,\n");
            writer.write("john doe,john.doe@email.com,12345678910,password,,,\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter writer = new FileWriter("DB/teste/test_event.csv")) {
            writer.write("id,ownerCPF,name,date,local,organization,description,attendeesList,articleList\n");
            writer.write("1,12345678910,Event One,2023-10-01,Location One,Org One,Description One,,\n");
            writer.write("2,987654321,Event Two,2023-11-01,Location Two,Org Two,Description Two,,\n");
        }

        // Ensure the test event exists
        testEvent =  new Event("1", "123456789", "Test Event", "2023-10-10", "Location", "Organization", "Description", "12345678910", "");
        testUser = new User("john doe", "john.doe@email.com", "12345678910", "password", "1","", "" );
        testUser2 = new User("john doe", "john.doe2@email.com", "12345678912", "password", "", "", "");

        assertNotNull(testEvent, "Test event should be created successfully");
        assertNotNull(testUser, "Test user should be created and logged in successfully");
    }

    @Test
    public void testLoginUser() {
        UserInterface user = facade.loginUser("12345678910", "password");
        assertNotNull(user);
        assertEquals("12345678910", user.getCPF());
    }

    @Test
    public void testSignUpUser() {
        UserInterface user = facade.signUpUser("Test User", "11111111112", "test@example.com", "password");
        assertNotNull(user);
        assertEquals("11111111112", user.getCPF());
    }

    @Test
    public void testCreateEvent() {
        EventInterface event = facade.createEvent(testUser, "Test Event", "Description", "2023-10-10", "Location", "Organization");
        assertNotNull(event, "The event should be created successfully");
        assertEquals("Test Event", event.getName(), "The event name should match");
    }

    @Test
    public void testGetAllEvents() {
        List<EventInterface> events = facade.getAllEvents();
        assertNotNull(events, "The event list should not be null");
        assertFalse(events.isEmpty(), "The event list should not be empty");
    }

    @Test
    public void testGetEventsIn() {
        List<EventInterface> events = facade.getEventsIn(testUser.getCPF());
        assertNotNull(events, "The event list should not be null");
        // Additional assertions can be added based on expected behavior
    }

    @Test
    public void testGetAllEventsByUser() {
        List<EventInterface> events = facade.getAllEventsByUser(testUser.getCPF());
        assertNotNull(events, "The event list should not be null");
        // Additional assertions can be added based on expected behavior
    }

    @Test
    public void testAddAttendeeOnList() {
        boolean result = facade.addAttendeeOnList(testUser2, testEvent);
        assertTrue(result, "The attendee should be added successfully");
        // Additional verification can be done by checking the event's attendee list
    }

    @Test
    public void testDeleteAttendeeOnList() {
        facade.addAttendeeOnList(testUser, testEvent); // First, add the attendee
        boolean result = facade.deleteAttendeeOnList(testUser, testEvent);
        assertTrue(result, "The attendee should be removed successfully");
        // Additional verification can be done by checking the event's attendee list
    }
}
