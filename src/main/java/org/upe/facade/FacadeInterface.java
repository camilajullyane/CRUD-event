package org.upe.facade;

import org.upe.persistence.interfaces.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FacadeInterface {
    // AuthController methods
    UserInterface loginUser(String cpf, String password);
    UserInterface signUpUser(String name, String cpf, String email, String password);

    // EventController methods
    EventInterface createEvent(UserInterface user, String name, String description, LocalDate beginDate, LocalDate endDate, String local, String organization);
    List<EventInterface> getAllEvents();
    EventInterface getEventByID(UUID id);
    boolean addAttendeeOnList(UserInterface user, EventInterface event);
    boolean deleteAttendeeOnList(UserInterface user, EventInterface event);
    boolean editEventName(EventInterface event, String newName);
    boolean editEventLocal(EventInterface event, String newLocal);
    boolean editEventDescription(EventInterface event, String newDescription);
    boolean editEventOrganization(EventInterface event, String newOrganization);
    boolean editEventBeginDate(EventInterface event, LocalDate newDate);
    boolean editEventEndDate(EventInterface event, LocalDate newDate);
    boolean deleteEvent(EventInterface event, UserInterface user);
    boolean addArticleOnList(ArticleInterface article, EventInterface event);

    // SubEventController methods
    SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate date,LocalDate endDate, String description);
    boolean editSubEventName(SubEventInterface subEvent, String newName);
    boolean editSubEventBeginDate(SubEventInterface subEvent, LocalDate newDate);
    boolean editSubEventEndDate(SubEventInterface subEvent, LocalDate newDate);
    boolean editSubEventDescription(SubEventInterface subEvent, String newDescription);
    SubEventInterface getSubEventByID(UUID id);
    boolean addAttendeeSubEventOnList(UserInterface user, SubEventInterface subEvent);
    boolean deleteSubEvent(UUID id);
    boolean removeAttendeeSubEventOnList(UserInterface user,SubEventInterface subEvent);

    // UserController methods
    boolean changeEmail(String userEmail, String newEmail);
    boolean changePassword(UserInterface user, String currentPassword, String newPassword);
    UserInterface getUserByCPF(String cpf);

    // ArticleController methods
    ArticleInterface createArticle(UserInterface user, String title, String articleAbstract);
    boolean submitArticle(ArticleInterface article, EventInterface event);
    boolean deleteArticle(ArticleInterface article);

    // SessionController methods
    SessionInterface createSession(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent);
    boolean deleteSession(UUID id);

    // CertificateController methods
    boolean generateCertificate();
}