package org.upe.facade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.JPAUtils.EntityManagerFactory;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.model.User;
import org.upe.persistence.interfaces.UserInterface;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private static FacadeInterface facade;
    private static UserInterface userWithEvent;
    private static UserInterface userSubscribedToEvent;
    private static UserInterface userDontSubscribedToEvent;
    private static SubEventInterface testSubEvent;
    private static EventInterface testEvent;
    private static ArticleInterface testArticle;

    @BeforeAll
    public static void setUP() {
        EntityManager entityManager = EntityManagerFactory.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        userWithEvent = new User("john doe", "12345678910", "john.doe@email.com","password");
        userSubscribedToEvent = new User("Dwight Schrute", "153489648123", "shrute@dundermifflin.com", "password");
        userDontSubscribedToEvent = new User("Michael Scott", "12345678913", "scott@dundermifflin.com", "password");

        testEvent = new Event("Google I/O", "A Google Event", LocalDate.now(), LocalDate.now(), "Google HQ", "Google", userWithEvent);

        testSubEvent = new SubEvent("Google I/O - Day 1", "Sundar Pichai", "Google HQ", LocalDate.now(), testEvent);

        entityManager.persist(userWithEvent);
        entityManager.persist(userDontSubscribedToEvent);
        entityManager.persist(userSubscribedToEvent);
        entityManager.persist(testEvent);
        entityManager.persist(testSubEvent);
        userSubscribedToEvent.subscribeToEvent(testEvent);
        testEvent.addAttendeeOnEvent(userSubscribedToEvent);
        entityManager.merge(testEvent);
        transaction.commit();
    }

    @BeforeEach
    public void setUp() throws IOException {
        facade = new Facade();
    }

    @Test
    public void testLoginUser() {
        UserInterface user = facade.loginUser("12345678910", "password");
        assertNotNull(user);
        assertEquals("12345678910", user.getCpf());
    }

    @Test
    public void testLoginUserWithWrongPassword() {
        UserInterface user = facade.loginUser("12345678910", "wrongpassword");
        assertNull(user);
    }

    @Test
    public void TestLoginUserWithWrongCPF() {
        UserInterface user = facade.loginUser("12345678911", "password");
        assertNull(user);
    }

    @Test
    public void testSignUpUser() {
        UserInterface user = facade.signUpUser("Jane Doe", "10987654321", "jane.doe@test.com", "password");
        assertNotNull(user);
        assertEquals("10987654321", user.getCpf());
    }

    @Test
    public void testSignUpUserWithAExistingCPF() {
        UserInterface user = facade.signUpUser("test", "12345678910", "test@gmail.com", "password");
        assertNull(user);
    }

    @Test
    public void testSignUpUserWithAExistingEmail() {
        UserInterface user = facade.signUpUser("test", "12345678910", "john.doe@email.com", "password");
        assertNull(user);
    }

    @Test
    public void testCreateEvent() {
        EventInterface event = facade.createEvent(userWithEvent, "Test Event", "Test Event Description", LocalDate.now(), LocalDate.now(), "Test Local", "Test Organization");
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
    }

    @Test
    public void testGetAllEvents() {
        List<EventInterface> events = facade.getAllEvents();
        assertNotNull(events);
        assertFalse(events.isEmpty());
    }

    @Test
    public void testGetEventByID() {
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertNotNull(event);
        assertEquals(testEvent.getId(), event.getId());
    }

    @Test
    public void testGetEventByIDWithWrongID() {
        EventInterface event = facade.getEventByID(UUID.randomUUID());
        assertNull(event);
    }

    @Test
    public void testAddAttendeeOnListWithAttendeeAlreadyOnList() {
        UserInterface user = new User("Test User", "10987654321", "user.test@gmail.com", "wordpass");
        user.subscribeToEvent(testEvent);
        testEvent.addAttendeeOnEvent(user);
        boolean result = facade.addAttendeeOnList(user, testEvent);
        assertFalse(result);
    }

    @Test
    public void testAddOwnerOnHisOwnEvent() {
        boolean result = facade.addAttendeeOnList(userWithEvent, testEvent);
        assertFalse(result);
    }

    @Test
    public void testAddAttendeeOnList() {
        boolean result = facade.addAttendeeOnList(userDontSubscribedToEvent, testEvent);
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        Optional<UserInterface> attendee = event.getAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(userDontSubscribedToEvent.getCpf()))
                .findFirst();
        assertTrue(attendee.isPresent());
    }

    @Test
    public void testDeleteAttendeeOnList() {
        boolean result = facade.deleteAttendeeOnList(userSubscribedToEvent, testEvent);
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        Optional<UserInterface> attendee = event.getAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(userSubscribedToEvent.getCpf()))
                .findFirst();
        assertFalse(attendee.isPresent());
    }

    @Test
    public void testEditEventName() {
        boolean result = facade.editEventName(testEvent, "New Event Name");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Event Name", event.getName());
    }

    @Test
    public void testEditEventLocal() {
        boolean result = facade.editEventLocal(testEvent, "New Local");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Local", event.getLocal());
    }

    @Test
    public void testEditEventDescription() {
        boolean result = facade.editEventDescription(testEvent, "New Description");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Description", event.getDescription());
    }

    @Test
    public void testEditEventOrganization() {
        boolean result = facade.editEventOrganization(testEvent, "New Organization");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Organization", event.getOrganization());
    }

    @Test
    public void testDeleteEvent() {

    }

    @Test
    public void testCreateSubEvent() {

    }

    @Test
    public void testEditSubEventName() {

    }

    @Test
    public void testEditSubEventDate() {

    }

    @Test
    public void testEditSubEventLocal() {

    }

    @Test
    public void testEditSubEventDescription() {

    }

    @Test
    public void testEditSubEventSpeaker() {

    }

    @Test
    public void testDeleteSubEvent() {

    }

    @Test
    public void testGetUserByCPF() {

    }

    @Test
    public void testChangeEmail() {

    }

    @Test
    public void testChangePassword() {

    }

    @Test
    public void testCreateArticle() {

    }
}
