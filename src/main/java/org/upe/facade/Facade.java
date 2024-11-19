package org.upe.facade;

import org.upe.controllers.*;
import org.upe.controllers.interfaces.*;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.SubEvent;

import java.time.LocalDate;
import java.util.Date;
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

    public boolean editEventDate(EventInterface event, LocalDate newDate) {
        return eventController.updateDate(event, newDate);
    }

    public boolean editEventHour(EventInterface event, String newHour) {
        return eventController.updateHour(event, newHour);
    }

    public boolean deleteEvent(EventInterface event, UserInterface user) {
        return eventController.deleteEvent(event, user);
    }

    // SubEventController methods
    public SubEventInterface createSubEvent(String parentEventID, String name, String local, String hour, String description, String speaker) {
        return subEventController.createSubEvent(parentEventID, name, local, hour, description, speaker);
    }

    public List<SubEventInterface> showAllSubEvents() {
        return subEventController.showAllSubEvents();
    }

    public List<SubEvent> getMySubEventsByParentEventID(String parentEventID, String userCPF) {
        return subEventController.getMySubEventsByParentEventID(parentEventID, userCPF);
    }

    public List<SubEventInterface> getAllSubEventsByEvent(String parentID) {
        return subEventController.getAllSubEventsByEvent(parentID);
    }

    public boolean editSubEventName(String id, String newName) {
        return subEventController.editSubEventName(id, newName);
    }

    public boolean editSubEventDate(String id, String newDate) {
        return subEventController.editSubEventDate(id, newDate);
    }

    public boolean editSubEventLocal(String id, String newLocal) {
        return subEventController.editSubEventLocal(id, newLocal);
    }

    public boolean editSubEventDescription(String id, String newDescription) {
        return subEventController.editSubEventDescription(id, newDescription);
    }

    public boolean editSubEventSpeaker(String id, String newSpeaker) {
        return subEventController.editSubEventSpeaker(id, newSpeaker);
    }

    public boolean editSubEventHour(String id, String newHour) {
        return subEventController.editSubEventHour(id, newHour);
    }

    public boolean deleteSubEvent(String id) {
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