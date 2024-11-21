package org.upe.facade;

import org.upe.controllers.*;
import org.upe.controllers.interfaces.*;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Facade implements FacadeInterface {
    private final AuthControllerInterface authController;
    private final EventControllerInterface eventController;
    private final SubEventControllerInterface subEventController;
    private final UserControllerInterface userController;
    private final ArticleControllerInterface articleController;

    public Facade() {
        this.authController = new AuthController();
        this.eventController = new EventController();
        this.subEventController = new SubEventController();
        this.userController = new UserController();
        this.articleController = new ArticleController();
    }

    // AuthController methods
    public UserInterface loginUser(String cpf, String password) {
        return authController.loginUser(cpf, password);
    }

    public UserInterface signUpUser(String name, String cpf, String email, String password) {
        return authController.signUpUser(name, cpf, email, password);
    }

    // EventController methods
    public EventInterface createEvent(UserInterface user, String name, String description, LocalDate beginDate, LocalDate endDate, String local, String organization) {
        return eventController.createEvent(user, name, description, beginDate, endDate, local, organization);
    }

    public List<EventInterface> getAllEvents() {
        return eventController.getAllEvents();
    }

    public EventInterface getEventByID(UUID id) {
        return eventController.getEventById(id);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        return eventController.addAttendeeOnList(user, event);
    }

    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        return eventController.deleteAttendeeOnList(user, event);
    }

    public boolean editEventName(EventInterface event, String newName) {
        return eventController.updateName(event, newName);
    }

    public boolean editEventLocal(EventInterface event, String newLocal) {
        return eventController.updateLocal(event, newLocal);
    }

    public boolean editEventDescription(EventInterface event, String newDescription) {
        return eventController.updateDescription(event, newDescription);
    }

    public boolean editEventOrganization(EventInterface event, String newOrganization) {
        return eventController.updateOrganization(event, newOrganization);
    }

    public boolean deleteEvent(EventInterface event, UserInterface user) {
        return eventController.deleteEvent(event, user);
    }

    // SubEventController methods
    public SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate date, String description, String speaker) {
        return subEventController.createSubEvent(parentEvent, name, date, description, speaker);
    }

    public boolean editSubEventName(SubEventInterface subEvent, String newName) {
        return subEventController.editSubEventName(subEvent, newName);
    }

    public boolean editSubEventDate(SubEventInterface subEvent, LocalDate newDate) {
        return subEventController.editSubEventDate(subEvent, newDate);
    }

    public boolean editSubEventDescription(SubEventInterface subEvent, String newDescription) {
        return subEventController.editSubEventDescription(subEvent, newDescription);
    }

    public boolean editSubEventSpeaker(SubEventInterface subEvent, String newSpeaker) {
        return subEventController.editSubEventSpeaker(subEvent, newSpeaker);
    }

    public boolean deleteSubEvent(UUID id) {
        return subEventController.deleteSubEvent(id);
    }

    // UserController methods

    public UserInterface getUserByCPF(String cpf) {
        return userController.getUserByCPF(cpf);
    }

    public boolean changeEmail(String userEmail, String newEmail) {
        return userController.changeEmail(userEmail, newEmail);
    }

    public boolean changePassword(UserInterface user, String currentPassword, String newPassword) {
        return userController.changePassword(user, currentPassword, newPassword);
    }

    // ArticlesController methods

    public void createArticle(UserInterface user, String name, String articleAbstract) {
        articleController.createArticle(user, name, articleAbstract);
    }

    public boolean submitArticle(ArticleInterface article, EventInterface event) {
        return articleController.submitArticle(article, event);
    }



}