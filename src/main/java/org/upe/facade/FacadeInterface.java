package org.upe.facade;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
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
    boolean deleteEvent(EventInterface event, UserInterface user);

    // SubEventController methods
    SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate date, String description, String speaker);
    boolean editSubEventName(SubEventInterface subEvent, String newName);
    boolean editSubEventDate(SubEventInterface subEvent, LocalDate newDate);
    boolean editSubEventDescription(SubEventInterface subEvent, String newDescription);
    boolean editSubEventSpeaker(SubEventInterface subEvent, String newSpeaker);
    SubEventInterface getSubEventByID(UUID id);
    boolean deleteSubEvent(UUID id);

    // UserController methods
    boolean changeEmail(String userEmail, String newEmail);
    boolean changePassword(UserInterface user, String currentPassword, String newPassword);
    UserInterface getUserByCPF(String cpf);

    // ArticleController methods
    void createArticle(UserInterface user, String name, String articleAbstract);
    boolean submitArticle(ArticleInterface article, EventInterface event);
}