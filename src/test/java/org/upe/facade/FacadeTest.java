package org.upe.facade;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.JPAUtils.JPAUtils;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.oldModel.Event;
import org.upe.persistence.oldModel.User;
import org.upe.persistence.oldModel.SubEvent;
import org.upe.persistence.repository.ArticleUtility;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.SubEventUtility;
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
    private ArticleInterface testArticle;

    @BeforeEach
    public void setUp() throws IOException {
        UserUtility.setCsvFilePath("DB/teste/test_user.csv");
        EventUtility.setCsvFilePath("DB/teste/test_event.csv");
        SubEventUtility.setCsvFilePath("DB/teste/test_subevent.csv");
        ArticleUtility.setCsvFilePath("DB/teste/test_article.csv");
        JPAUtils.setPersistenceUnitName("teste-jpa");

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

        try (FileWriter subEventWriter = new FileWriter("DB/teste/test_subevent.csv")) {
            subEventWriter.write("id,parentEventID,name,date,hour,local,description,speaker,attendeesList\n");
            subEventWriter.write("1,1,Sub Event 1,2023-10-10,10:00,Sub Location 1,Sub Description 1,Speaker 1,\n");
            subEventWriter.write("2,1,Sub Event 2,2023-10-10,11:00,Sub Location 2,Sub Description 2,Speaker 2,\n");
        }

        try (FileWriter articleWriter = new FileWriter("DB/teste/test_article.csv")) {
            articleWriter.write("name,articleID,userCPF,articleAbstract\n");
            articleWriter.write("Test Article,1,12345678910,This is a test article abstract.\n");
            articleWriter.write("Test Article 2,2,12345678910,This is another test article abstract.\n");
        }

        testEvent =  new Event("1", "123456789", "Test Event", "2023-10-10", "Location", "Organization", "Description", "12345678910", "");
        assertNotNull(testEvent, "Test event should be created successfully");

        testUser = new User("john doe", "john.doe@email.com", "12345678910", "password", "1","", "" );
        testUser2 = new User("john doe", "john.doe2@email.com", "12345678912", "password", "", "", "");
        assertNotNull(testUser, "Test user should be created and logged in successfully");
        assertNotNull(testUser2, "Test user should be created and logged in successfully");

    }

    @AfterAll
    public static void tearDown() {
        JPAUtils.close();
    }

    @Test
    public void testLoginUser() {
        UserInterface user = facade.loginUser("12345678910", "password");
        assertNotNull(user);
        assertEquals("12345678910", user.getCPF());
    }

    @Test
    public void testSignUpUser() {
        UserInterface user = facade.signUpUser("Test User", "11111111112", "test@example.com","password");
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
    public void testGetEventByID() {
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertNotNull(event, "The event should be retrieved successfully");
        assertEquals(testEvent.getId(), event.getId(), "The event ID should match");
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

    @Test
    public void testEditEventName() {
        boolean result = facade.editEventName(testEvent.getId(), "New Event Name");
        assertTrue(result, "The event name should be edited successfully");
    }

    @Test
    public void testEditEventLocal() {
        boolean result = facade.editEventLocal(testEvent.getId(), "New Location");
        assertTrue(result, "The event location should be edited successfully");
    }

    @Test
    public void testEditEventDescription() {
        boolean result = facade.editEventDescription(testEvent.getId(), "New Description");
        assertTrue(result, "The event description should be edited successfully");
    }

    @Test
    public void testEditEventOrganization() {
        boolean result = facade.editEventOrganization(testEvent.getId(), "New Organization");
        assertTrue(result, "The event organization should be edited successfully");
    }

    @Test
    public void testEditEventDate() {
        boolean result = facade.editEventDate(testEvent.getId(), "2023-12-12");
        assertTrue(result, "The event date should be edited successfully");
    }

    @Test
    public void testDeleteEvent() {
        boolean result = facade.deleteEvent(testEvent.getId(), testUser);
        assertTrue(result, "The event should be deleted successfully");

        List<EventInterface> events = facade.getAllEvents();
        assertFalse(events.contains(testEvent), "The event list should not contain the deleted event");
    }

    @Test
    public void testCreateSubEvent() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        assertNotNull(subEvent, "The sub-event should be created successfully");
        assertEquals("Sub Event", subEvent.getName(), "The sub-event name should match");
    }

    @Test
    public void testShowAllSubEvents() {
        List<SubEventInterface> subEvents = facade.showAllSubEvents();
        assertNotNull(subEvents, "The sub-event list should not be null");
        assertEquals(2, subEvents.size(), "The sub-event list should contain 2 sub-events");
    }

    @Test
    public void testGetMySubEventsByParentEventID() {
        List<SubEvent> subEvents = facade.getMySubEventsByParentEventID(testEvent.getId(), testUser.getCPF());
        assertNotNull(subEvents, "The sub-event list should not be null");
        // Additional assertions can be added based on expected behavior
    }

    @Test
    public void testGetAllSubEventsByEvent() {
        List<SubEventInterface> subEvents = facade.getAllSubEventsByEvent(testEvent.getId());
        assertNotNull(subEvents, "The sub-event list should not be null");
        // Additional assertions can be added based on expected behavior
    }

    @Test
    public void testEditSubEventName() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        boolean result = facade.editSubEventName(subEvent.getId(), "New Sub Event Name");
        assertTrue(result, "The sub-event name should be edited successfully");
    }

    @Test
    public void testEditSubEventDate() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        boolean result = facade.editSubEventDate(subEvent.getId(), "2023-12-12");
        assertTrue(result, "The sub-event date should be edited successfully");
    }

    @Test
    public void testEditSubEventLocal() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        boolean result = facade.editSubEventLocal(subEvent.getId(), "New Sub Location");
        assertTrue(result, "The sub-event location should be edited successfully");
    }

    @Test
    public void testEditSubEventDescription() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        boolean result = facade.editSubEventDescription(subEvent.getId(), "New Sub Description");
        assertTrue(result, "The sub-event description should be edited successfully");
    }

    @Test
    public void testEditSubEventSpeaker() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        boolean result = facade.editSubEventSpeaker(subEvent.getId(), "New Speaker");
        assertTrue(result, "The sub-event speaker should be edited successfully");
    }

    @Test
    public void testDeleteSubEvent() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent.getId(), "Sub Event", "Sub Location", "10:00", "Sub Description", "Speaker");
        boolean result = facade.deleteSubEvent(subEvent.getId());
        assertTrue(result, "The sub-event should be deleted successfully");
    }

    @Test
    public void testDeleteAttendeeEvent() {
        boolean result = facade.deleteAttendeeEvent(testUser.getCPF(), testEvent.getId());
        assertTrue(result, "The attendee should be deleted from the event successfully");
    }

    @Test
    public void testGetUserByCPF() {
        UserInterface user = facade.getUserByCPF(testUser.getCPF());
        assertNotNull(user, "The user should be retrieved successfully");
        assertEquals(testUser.getCPF(), user.getCPF(), "The user CPF should match");
    }

    @Test
    public void testUserEventsIn() {
        List<EventInterface> events = facade.userEventsIn(testUser.getCPF());
        assertNotNull(events, "The event list should not be null");
    }

    @Test
    public void testDeleteAttendeeFromEvent() {
        boolean result = facade.deleteAttendeeFromEvent(testUser, testEvent);
        assertTrue(result, "The attendee should be deleted from the event successfully");
    }

    @Test
    public void testChangeEmail() {
        boolean result = facade.changeEmail(testUser.getEmail(), "newemail@example.com");
        assertTrue(result, "The email should be changed successfully");
    }

    @Test
    public void testChangePassword() {
        boolean result = facade.changePassword(testUser.getCPF(), "password", "newpassword");
        assertTrue(result, "The password should be changed successfully");
    }

    @Test
    public void testCreateArticle() {
        facade.createArticle(testUser, "Test Article", "This is a test article abstract.");
        List<ArticleInterface> articles = facade.getAllArticlesByUser(testUser.getCPF());
        assertNotNull(articles, "The article list should not be null");
        assertEquals(3, articles.size(), "The article list should contain 2 articles");
        assertEquals("Test Article", articles.getFirst().getName(), "The article name should match");
    }

    @Test
    public void testGetAllArticlesByUser() {
        List<ArticleInterface> articles = facade.getAllArticlesByUser(testUser.getCPF());
        assertNotNull(articles, "The article list should not be null");
        assertEquals(2, articles.size(), "The article list should contain 2 articles");
    }
}
