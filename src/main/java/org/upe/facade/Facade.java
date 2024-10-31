package org.upe.facade;

import org.upe.controllers.*;
import org.upe.controllers.interfaces.*;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.SubEvent;

import java.util.ArrayList;
import java.util.List;

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
    public EventInterface createEvent(UserInterface user, String name, String description, String date, String local, String organization) {
        return eventController.createEvent(user, name, description, date, local, organization);
    }

    public List<EventInterface> getAllEvents() {
        return eventController.getAllEvents();
    }

    public List<EventInterface> getEventsIn(String ownerCPF) {
        return eventController.getEventsIn(ownerCPF);
    }

    public EventInterface getEventByID(String id) {
        return eventController.getEventById(id);
    }

    public List<EventInterface> getAllEventsByUser(String ownerCPF) {
        return eventController.getAllEventsByUser(ownerCPF);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        return eventController.addAttendeeOnList(user, event);
    }

    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        return eventController.deleteAttendeeOnList(user, event);
    }

    public boolean editEventName(String id, String newName) {
        return eventController.editEventName(id, newName);
    }

    public boolean editEventLocal(String id, String newLocal) {
        return eventController.editEventLocal(id, newLocal);
    }

    public boolean editEventDescription(String id, String newDescription) {
        return eventController.editEventDescription(id, newDescription);
    }

    public boolean editEventOrganization(String id, String newOrganization) {
        return eventController.editEventOrganization(id, newOrganization);
    }

    public boolean editEventDate(String id, String newDate) {
        return eventController.editEventDate(id, newDate);
    }

    public boolean deleteEvent(String id, UserInterface user) {
        return eventController.deleteEvent(id, user);
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
    public boolean deleteAttendeeEvent(String userCPF, String eventID) {
        return userController.deleteAttendeeEvent(userCPF, eventID);
    }

    public UserInterface getUserByCPF(String cpf) {
        return userController.getUserByCPF(cpf);
    }

    public List<EventInterface> userEventsIn(String ownerCPF) {
        return userController.userEventsIn(ownerCPF);
    }

    public boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event) {
        return userController.deleteAttendeeFromEvent(user, event);
    }

    public boolean changeEmail(String userEmail, String newEmail) {
        return userController.changeEmail(userEmail, newEmail);
    }

    public boolean changePassword(String cpf, String currentPassword, String newPassword) {
        return userController.changePassword(cpf, currentPassword, newPassword);
    }

    public void createArticle(UserInterface user, String name, String articleAbstract) {
        articleController.createArticle(user, name, articleAbstract);
    }

    public List<ArticleInterface> getAllArticlesByUser(String userCPF) {
        return articleController.getAllArticlesByUser(userCPF);
    }

    public boolean submitArticle(ArticleInterface article, EventInterface event) {
        return articleController.submitArticle(article, event);
    }
}