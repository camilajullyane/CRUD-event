package org.upe.facade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.SubEvent;
import org.upe.persistence.model.User;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.utils.PasswordUtil;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private static FacadeInterface facade;
    private static UserInterface userWithEvent;
    private static UserInterface userSubscribedToEvent;
    private static UserInterface userDontSubscribedToEvent;
    private static UserInterface userSubscribedToEvent2;

    private static UserInterface userSubscribedToSubEvent;
    private static UserInterface userDontSubscribedToSubEvent;
    private static SubEventInterface testSubEvent;
    private static EventInterface testEvent;
    private static ArticleInterface testArticle;

    @BeforeAll
    public static void setUP() {
        EntityManager entityManager = EntityManagerFactory.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        userWithEvent =  User.Builder().withName("John doe").withCpf("12345678910").withEmail("john.doe@email.com").withPassword("password").build();
        userSubscribedToEvent = User.Builder().withName("Dwight Schrute").withCpf("15348964812").withEmail("shrute@dundermifflin.com").withPassword("password").build();
        userSubscribedToEvent2 = User.Builder().withName("Jim Halpert").withCpf("15348964855").withEmail("jim@dundermifflin.com").withPassword("password").build();
        userDontSubscribedToEvent = User.Builder().withName("Michael Scott").withCpf("12345678913").withEmail("scott@dundermifflin.com").withPassword("password").build();

        userSubscribedToSubEvent = User.Builder().withName("Pam Beesly").withCpf("15348964813").withEmail("beesly@example.com").withPassword("password").build();
        userDontSubscribedToSubEvent = User.Builder().withName("Ryan Howard").withCpf("15348964892").withEmail("howard@example.com").withPassword("password").build();

        testEvent = Event.Builder()
                .withName("Google I/O")
                .withDescription("A Google Event")
                .withBeginDate(LocalDate.now())
                .withEndDate(LocalDate.now())
                .withLocal("Google HQ")
                .withOrganization("Google")
                .withOwner((User) userWithEvent)
                .build();

        testSubEvent = SubEvent.Builder().withName("Google I/O - Day 1").withBeginDate(LocalDate.now()).withEndDate(LocalDate.now()).withParentEvent(testEvent).build();

        entityManager.persist(userWithEvent);
        entityManager.persist(userDontSubscribedToEvent);
        entityManager.persist(userSubscribedToEvent);
        entityManager.persist(userSubscribedToEvent2);
        entityManager.persist(testEvent);

        entityManager.persist(testSubEvent);
        entityManager.persist(userSubscribedToSubEvent);
        entityManager.persist(userDontSubscribedToSubEvent);
        testSubEvent.addAttendeeOnSubEvent(userSubscribedToSubEvent);
        entityManager.merge(userSubscribedToSubEvent);
        entityManager.merge(testSubEvent);

        userSubscribedToEvent.subscribeToEvent(testEvent);
        userSubscribedToEvent2.subscribeToEvent(testEvent);
        testEvent.addAttendeeOnEvent(userSubscribedToEvent);
        testEvent.addAttendeeOnEvent(userSubscribedToEvent2);

        entityManager.merge(userSubscribedToEvent);
        entityManager.merge(userSubscribedToEvent2);
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

    //Testes Event

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
        boolean result = facade.addAttendeeOnList(userSubscribedToEvent, testEvent);
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
        boolean result = facade.deleteAttendeeOnList(userSubscribedToEvent2, testEvent);
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        Optional<UserInterface> attendee = event.getAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(userSubscribedToEvent2.getCpf()))
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
        boolean result = facade.deleteEvent(testEvent, userWithEvent);
        assertTrue(result);
    }

    //Testes SubEvent

    @Test
    public void testCreateSubEvent() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent, "Test SubEvent", LocalDate.now(), LocalDate.now(),"Test SubEvent Description");
        assertNotNull(subEvent);
        assertEquals("Test SubEvent", subEvent.getName());
    }

    @Test
    public void testAddAttendeeOnSubEventList() {
        boolean result = facade.addAttendeeSubEventOnList(userDontSubscribedToSubEvent, testSubEvent);
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        Optional<UserInterface> attendee = subEvent.getSubEventAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(userDontSubscribedToSubEvent.getCpf()))
                .findFirst();
        assertTrue(attendee.isPresent());

    }

    @Test
    public void testAddAttendeeOnSubEventListWithAttendeeAlreadyOnList() {
        boolean result = facade.addAttendeeSubEventOnList(userSubscribedToSubEvent, testSubEvent);
        assertFalse(result);
    }

    @Test
    public void testEditSubEventName() {
        boolean result = facade.editSubEventName(testSubEvent, "New SubEvent Name");
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        assertEquals("New SubEvent Name", subEvent.getName());
    }

    @Test
    public void testEditSubEventDate() {
        LocalDate newDate = LocalDate.now().plusDays(1);
        boolean result = facade.editSubEventDate(testSubEvent, newDate);
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        assertEquals(newDate, subEvent.getBeginDate());
    }

    @Test
    public void testEditSubEventDescription() {
        boolean result = facade.editSubEventDescription(testSubEvent, "New Description");
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        assertEquals("New Description", subEvent.getDescription());
    }


    @Test
    public void testDeleteSubEvent() {
        boolean result = facade.deleteSubEvent(testSubEvent.getId());
        assertTrue(result);
    }

    @Test
    public void testGetUserByCPF() {
        UserInterface user = facade.getUserByCPF("12345678910");
        assertNotNull(user);
        assertEquals("12345678910", user.getCpf());
    }
    @Test
    public void testGetUserByCPFWithWrongCPF() {
        UserInterface user = facade.getUserByCPF("12345568911");
        assertNull(user);
    }

    @Test
    public void testChangeEmail() {
        boolean result = facade.changeEmail(userWithEvent.getEmail(), "newemail@gmail.com");
        assertTrue(result);
        UserInterface user = facade.getUserByCPF(userWithEvent.getCpf());
        assertEquals("newemail@gmail.com", user.getEmail());
    }

    @Test
    public void testChangePassword() {
        boolean result = facade.changePassword(userWithEvent, "password", "newpassword");
        assertTrue(result);
        User user = (User) facade.getUserByCPF(userWithEvent.getCpf());
        assertTrue(PasswordUtil.matches("newpassword", user.getPassword()));
    }

    @Test
    public void testCreateArticle() {

    }
}
