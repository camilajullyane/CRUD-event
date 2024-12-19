package org.upe.facade;

import org.upe.controllers.*;
import org.upe.controllers.interfaces.*;
import org.upe.persistence.dao.*;
import org.upe.persistence.interfaces.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Facade implements FacadeInterface {
    private final AuthControllerInterface authController;
    private final EventControllerInterface eventController;
    private final SubEventControllerInterface subEventController;
    private final UserControllerInterface userController;
    private final ArticleControllerInterface articleController;
    private final SessionControllerInterface sessionController;
    private final CertificateControllerInterface certificateController;

    public Facade() {
        this.certificateController = new CertificateController(new CertificateDAO(), new SubEventDAO());
        this.authController = new AuthController(new UserDAO());
        this.eventController = new EventController(new EventDAO(), new UserDAO(), new ArticleDAO());
        this.subEventController = new SubEventController(new SubEventDAO(), new UserDAO());
        this.userController = new UserController(new UserDAO());
        this.articleController = new ArticleController(new ArticleDAO(), new EventDAO());
        this.sessionController = new SessionController(new SessionDAO());

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

    public boolean editEventBeginDate(EventInterface event, LocalDate newDate) {
        return eventController.updateBeginDate(event, newDate);
    }

    public boolean editEventEndDate(EventInterface event, LocalDate newDate) {
        return eventController.updateEndDate(event, newDate);
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

    public boolean addArticleOnList(ArticleInterface article, EventInterface event) {
        return eventController.addArticleOnList(article, event);
    }



    // SubEventController methods
    public SubEventInterface createSubEvent(EventInterface parentEvent, String name, LocalDate beginDate, LocalDate endDate, String description) {
        return subEventController.createSubEvent(parentEvent, name, beginDate,endDate, description);
    }

    public boolean addAttendeeSubEventOnList(UserInterface user, SubEventInterface subEvent) {
        return subEventController.addAttendeeSubEventOnList(user, subEvent);
    }

    public boolean editSubEventName(SubEventInterface subEvent, String newName) {
        return subEventController.editSubEventName(subEvent, newName);
    }

    public boolean editSubEventBeginDate(SubEventInterface subEvent, LocalDate newDate) {
        return subEventController.editSubEventBeginDate(subEvent, newDate);
    }

    public boolean editSubEventEndDate(SubEventInterface subEvent, LocalDate newDate) {
        return subEventController.editSubEventEndDate(subEvent, newDate);
    }

    public boolean editSubEventDescription(SubEventInterface subEvent, String newDescription) {
        return subEventController.editSubEventDescription(subEvent, newDescription);
    }

    public SubEventInterface getSubEventByID(UUID id) {
        return subEventController.getSubEventByID(id);
    }

    public boolean deleteSubEvent(UUID id) {
        return subEventController.deleteSubEvent(id);
    }

    public boolean removeAttendeeSubEventOnList(UserInterface user,SubEventInterface subEvent) {
        return subEventController.removeAttendeeSubEventOnList(user,subEvent);
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
    public ArticleInterface createArticle(UserInterface user, String title, String articleAbstract) {
        return articleController.createArticle(user, title, articleAbstract);
    }

    public boolean submitArticle(ArticleInterface article, EventInterface event) {
        return articleController.submitArticle(article, event);
    }

    public boolean deleteArticle(ArticleInterface article) {
        return articleController.deleteArticle(article);
    }



    //SessionController methods
    public SessionInterface createSession(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent) {
        return sessionController.create(name,date, beginHour, endHour, local, description, speaker, parentSubEvent);
    }

    public boolean deleteSession(UUID id) {
        return sessionController.delete(id);
    }

    public boolean generateCertificate() {return certificateController.generateCertificate();}
}