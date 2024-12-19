package org.upe.facade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.*;
import org.upe.persistence.model.*;
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
    private static SubEventInterface testSubEvent2;
    private static EventInterface testEvent;
    private static EventInterface testEventWithArticle;
    private static ArticleInterface testArticle;
    private static ArticleInterface testArticleToBeDeleted;
    private static SessionInterface testSession;

    @BeforeAll
    public static void setUP() {
        EntityManager entityManager = EntityManagerFactory.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        userWithEvent =  User.builder().withName("John doe").withCpf("12345678910").withEmail("john.doe@email.com").withPassword("password").build();
        userSubscribedToEvent = User.builder().withName("Dwight Schrute").withCpf("15348964812").withEmail("shrute@dundermifflin.com").withPassword("password").build();
        userSubscribedToEvent2 = User.builder().withName("Jim Halpert").withCpf("15348964855").withEmail("jim@dundermifflin.com").withPassword("password").build();
        userDontSubscribedToEvent = User.builder().withName("Michael Scott").withCpf("12345678913").withEmail("scott@dundermifflin.com").withPassword("password").build();

        userSubscribedToSubEvent = User.builder().withName("Pam Beesly").withCpf("15348964813").withEmail("beesly@example.com").withPassword("password").build();
        userDontSubscribedToSubEvent = User.builder().withName("Ryan Howard").withCpf("15348964892").withEmail("howard@example.com").withPassword("password").build();

        testEvent = Event.builder()
                .withName("Google I/O")
                .withDescription("A Google Event")
                .withBeginDate(LocalDate.now())
                .withEndDate(LocalDate.now())
                .withLocal("Google HQ")
                .withOrganization("Google")
                .withOwner((User) userWithEvent)
                .build();

        testEventWithArticle = Event.builder()
                .withName("Google I/O")
                .withDescription("A Google Event2")
                .withBeginDate(LocalDate.now())
                .withEndDate(LocalDate.now())
                .withLocal("Google HQ2")
                .withOrganization("Google2")
                .withOwner((User) userWithEvent)
                .build();


        testSubEvent = SubEvent.builder().withName("Google I/O - Day 1").withBeginDate(LocalDate.now()).withEndDate(LocalDate.now()).withParentEvent(testEvent).build();
        testSubEvent2 = SubEvent.builder().withName("Google I/O - Day 1").withBeginDate(LocalDate.of(2024, 12, 15)).withEndDate(LocalDate.of(2024, 12, 16)).withParentEvent(testEvent).build();


        testSession = Session.builder()
                .withName("Test Session")
                .withDate(LocalDate.now())
                .withBeginHour(LocalDate.now().atStartOfDay())
                .withEndHour(LocalDate.now().atStartOfDay().plusHours(1))
                .withLocal("Test Local")
                .withDescription("Test Description")
                .withSpeaker("Test Speaker")
                .withParentSubEvent(testSubEvent)
                .build();

        testArticle = Article.builder()
                .withArticleAbstract("Abstract")
                .withTitle("Test Article")
                .build();

        testArticleToBeDeleted = Article.builder()
                .withArticleAbstract("Abstract")
                .withTitle("Test Article")
                .build();

        entityManager.persist(userWithEvent);
        entityManager.persist(userDontSubscribedToEvent);
        entityManager.persist(userSubscribedToEvent);
        entityManager.persist(userSubscribedToEvent2);
        entityManager.persist(testEvent);
        entityManager.persist(testEventWithArticle);

        entityManager.persist(testArticle);
        entityManager.persist(testArticleToBeDeleted);
        entityManager.persist(testSubEvent2);

        entityManager.persist(testSubEvent);
        entityManager.persist(userSubscribedToSubEvent);
        entityManager.persist(userDontSubscribedToSubEvent);
        testSubEvent.addAttendeeOnSubEvent(userSubscribedToSubEvent);
        entityManager.merge(userSubscribedToSubEvent);
        entityManager.merge(testSubEvent);
        entityManager.merge(testSession);

        userSubscribedToEvent.subscribeToEvent(testEvent);
        userSubscribedToEvent2.subscribeToEvent(testEvent);
        testEvent.addAttendeeOnEvent(userSubscribedToEvent);
        testEvent.addAttendeeOnEvent(userSubscribedToEvent2);

        entityManager.merge(userSubscribedToEvent);
        entityManager.merge(userSubscribedToEvent2);
        entityManager.merge(testEvent);
        entityManager.merge(testEventWithArticle);

        entityManager.merge(testArticle);
        entityManager.merge(testArticleToBeDeleted);



        transaction.commit();
    }

    @BeforeEach
    public void setUp() throws IOException {
        facade = new Facade();
    }

    @Test
    void testLoginUser() {
        UserInterface user = facade.loginUser("12345678910", "password");
        assertNotNull(user);
        assertEquals("12345678910", user.getCpf());
    }

    @Test
    void testLoginUserWithWrongPassword() {
        UserInterface user = facade.loginUser("12345678910", "wrongpassword");
        assertNull(user);
    }

    @Test
    void TestLoginUserWithWrongCPF() {
        UserInterface user = facade.loginUser("12345678911", "password");
        assertNull(user);
    }

    @Test
    void testSignUpUser() {
        UserInterface user = facade.signUpUser("Jane Doe", "10987654321", "jane.doe@test.com", "password");
        assertNotNull(user);
        assertEquals("10987654321", user.getCpf());
    }

    @Test
    void testSignUpUserWithAExistingCPF() {
        UserInterface user = facade.signUpUser("test", "12345678910", "test@gmail.com", "password");
        assertNull(user);
    }

    @Test
    void testSignUpUserWithAExistingEmail() {
        UserInterface user = facade.signUpUser("test", "12345678910", "john.doe@email.com", "password");
        assertNull(user);
    }

    //Testes Event

    @Test
    void testCreateEvent() {
        EventInterface event = facade.createEvent(userWithEvent, "Test Event", "Test Event Description", LocalDate.now(), LocalDate.now(), "Test Local", "Test Organization");
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
    }

    @Test
    void testGetAllEvents() {
        List<EventInterface> events = facade.getAllEvents();
        assertNotNull(events);
        assertFalse(events.isEmpty());
    }

    @Test
    void testGetEventByID() {
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertNotNull(event);
        assertEquals(testEvent.getId(), event.getId());
    }

    @Test
    void testGetEventByIDWithWrongID() {
        EventInterface event = facade.getEventByID(UUID.randomUUID());
        assertNull(event);
    }
    @Test
    void testAddAttendeeOnListWithAttendeeAlreadyOnList() {
        boolean result = facade.addAttendeeOnList(userSubscribedToEvent, testEvent);
        assertFalse(result);
    }

    @Test
    void testAddOwnerOnHisOwnEvent() {
        boolean result = facade.addAttendeeOnList(userWithEvent, testEvent);
        assertFalse(result);
    }

    @Test
    void testAddAttendeeOnList() {
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
    void testAddArticleOnList() {
        boolean result = facade.addArticleOnList(testArticle, testEventWithArticle);
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEventWithArticle.getId());
        Optional<ArticleInterface> article = event.getArticles()
                .stream()
                .filter(a -> a.getId().equals(testArticle.getId()))
                .findFirst();
        assertTrue(article.isPresent());
    }

    @Test
    void testDeleteAttendeeOnList() {
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
    void testEditEventName() {
        boolean result = facade.editEventName(testEvent, "New Event Name");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Event Name", event.getName());
    }

    @Test
    void testEditEventLocal() {
        boolean result = facade.editEventLocal(testEvent, "New Local");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Local", event.getLocal());
    }

    @Test
    void testEditEventDescription() {
        boolean result = facade.editEventDescription(testEvent, "New Description");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Description", event.getDescription());
    }

    @Test
    void testEditEventOrganization() {
        boolean result = facade.editEventOrganization(testEvent, "New Organization");
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        assertEquals("New Organization", event.getOrganization());
    }

    @Test
    void testDeleteEvent() {
        boolean result = facade.deleteEvent(testEvent, userWithEvent);
        assertTrue(result);
    }

    // Testes Certificate
    @Test
    void testGenerateCertificate() {
        boolean result = facade.generateCertificate();
        assertTrue(result);
    }


    // Testes SubEvent

    @Test
    void testCreateSubEvent() {
        SubEventInterface subEvent = facade.createSubEvent(testEvent, "Test SubEvent", LocalDate.now(), LocalDate.now(),"Test SubEvent Description");
        assertNotNull(subEvent);
        assertEquals("Test SubEvent", subEvent.getName());
    }

    @Test
    void testAddAttendeeOnSubEventList() {
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
    void testAddAttendeeOnSubEventListWithAttendeeAlreadyOnList() {
        boolean result = facade.addAttendeeSubEventOnList(userSubscribedToSubEvent, testSubEvent);
        assertFalse(result);
    }

    @Test
    void testEditSubEventName() {
        boolean result = facade.editSubEventName(testSubEvent, "New SubEvent Name");
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        assertEquals("New SubEvent Name", subEvent.getName());
    }

    @Test
    void testEditSubEventDate() {
        LocalDate newDate = LocalDate.now().plusDays(1);
        boolean result = facade.editSubEventDate(testSubEvent, newDate);
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        assertEquals(newDate, subEvent.getBeginDate());
    }

    @Test
    void testEditSubEventDescription() {
        boolean result = facade.editSubEventDescription(testSubEvent, "New Description");
        assertTrue(result);
        SubEventInterface subEvent = facade.getSubEventByID(testSubEvent.getId());
        assertEquals("New Description", subEvent.getDescription());
    }


    @Test
    void testDeleteSubEvent() {
        boolean result = facade.deleteSubEvent(testSubEvent.getId());
        assertTrue(result);
    }

    @Test
    void testRemoveAttendeeSubEventOnList() {
        boolean result = facade.removeAttendeeSubEventOnList(userSubscribedToSubEvent, testSubEvent);
        assertTrue(result);
    }

    @Test
    void testGetUserByCPF() {
        UserInterface user = facade.getUserByCPF("12345678910");
        assertNotNull(user);
        assertEquals("12345678910", user.getCpf());
    }
    @Test
    void testGetUserByCPFWithWrongCPF() {
        UserInterface user = facade.getUserByCPF("12345568911");
        assertNull(user);
    }

    @Test
    void testChangeEmail() {
        boolean result = facade.changeEmail(userWithEvent.getEmail(), "newemail@gmail.com");
        assertTrue(result);
        UserInterface user = facade.getUserByCPF(userWithEvent.getCpf());
        assertEquals("newemail@gmail.com", user.getEmail());
    }

    @Test
    void testChangePassword() {
        boolean result = facade.changePassword(userWithEvent, "password", "newpassword");
        assertTrue(result);
        User user = (User) facade.getUserByCPF(userWithEvent.getCpf());
        assertTrue(PasswordUtil.matches("newpassword", user.getPassword()));
    }

    @Test
    void testCreateArticle() {
        ArticleInterface article = facade.createArticle(userWithEvent, "Test Article", "Test Article Abstract");
        assertNotNull(article);
        assertEquals("Test Article", article.getTitle());
    }

    @Test
    void testSubmitArticle() {
        boolean result = facade.submitArticle(testArticle, testEvent);
        assertTrue(result);
        EventInterface event = facade.getEventByID(testEvent.getId());
        Optional<ArticleInterface> article = event.getArticles()
                .stream()
                .filter(a -> a.getId().equals(testArticle.getId()))
                .findFirst();
        assertTrue(article.isPresent());
    }

    @Test
    void testDeleteArticle() {
        boolean result = facade.deleteArticle(testArticleToBeDeleted);
        assertTrue(result);
    }


    @Test
    void testCreateSession() {
        SessionInterface session = facade.createSession("Test Session", LocalDate.now(), LocalDate.now().atStartOfDay(), LocalDate.now().atStartOfDay().plusHours(1), "Test Local", "Test Description", "Test Speaker", testSubEvent);
        assertNotNull(session);
        assertEquals("Test Session", session.getName());
    }

    @Test
    void testDeleteSession() {
        boolean result = facade.deleteSession(testSession.getId());
        assertTrue(result);
    }
}