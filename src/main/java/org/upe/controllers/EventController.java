package org.upe.controllers;

import org.upe.controllers.interfaces.EventControllerInterface;
import org.upe.persistence.dao.ArticleDAO;
import org.upe.persistence.dao.EventDAO;
import org.upe.persistence.dao.UserDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

import java.time.LocalDate;
import java.util.*;

public class EventController implements EventControllerInterface {
    private final EventDAO eventDAO;
    private final UserDAO userDAO;
    private final ArticleDAO articleDAO;

    public EventController(EventDAO eventDAO, UserDAO userDAO, ArticleDAO articleDAO) {
        this.eventDAO = eventDAO;
        this.userDAO = userDAO;
        this.articleDAO = articleDAO;
    }

    public EventInterface createEvent(UserInterface user, String name, String description, LocalDate beginDate, LocalDate endDate, String local,
                                      String organization) {
        EventInterface event = eventDAO.create(name, description, beginDate, endDate, local, organization, user);
        user.addMyEventAsOwner(event);
        return event;
    }

    public List<EventInterface> getAllEvents() {
        List<EventInterface> events = eventDAO.getAll();

        return new ArrayList<>(events);
    }

    public boolean addAttendeeOnList(UserInterface user, EventInterface event) {
        if (event.getOwner().getId() == user.getId()) {
            return false;
        }

        Optional<UserInterface> attendee = event.getAttendeesList()
                .stream()
                .filter(u -> u.getCpf().equals(user.getCpf()))
                .findFirst();

        if(attendee.isPresent()) {
            return false;
        }

        user.subscribeToEvent(event);
        event.addAttendeeOnEvent(user);
        eventDAO.update(event);
        userDAO.update((User) user);
        return true;
    }

    public boolean addArticleOnList(ArticleInterface article, EventInterface event) {
        for (ArticleInterface a : event.getArticles()) {
            if (a.getId().equals(article.getId())) {
                return false;
            }
        }
        event.addArticleOnEvent(article);
        eventDAO.update(event);
        article.getSubmittedIn().add(event);
        articleDAO.update(article);
        return true;
    }


    public boolean deleteAttendeeOnList(UserInterface user, EventInterface event) {
        event.removeAttendeeOnEvent(user);
        user.unsubscribeToEvent(event);
        eventDAO.update(event);
        userDAO.update((User) user);
        return true;
    }

    public boolean updateName(EventInterface event, String newName) {
        event.setName(newName);
        eventDAO.update(event);
        return true;
    }

    public boolean updateLocal(EventInterface event, String newLocal) {
        event.setLocal(newLocal);
        eventDAO.update(event);
        return true;
    }

    public boolean updateBeginDate(EventInterface event, LocalDate newDate) {
        event.setBeginDate(newDate);
        eventDAO.update(event);
        return true;
    }

    public boolean updateEndDate(EventInterface event, LocalDate newDate) {
        event.setEndDate(newDate);
        eventDAO.update(event);
        return true;
    }

    public boolean updateDescription(EventInterface event, String newDescription) {
        event.setDescription(newDescription);
        eventDAO.update(event);
        return true;
    }

    public boolean updateOrganization(EventInterface event, String newOrganization) {
        event.setOrganization(newOrganization);
        eventDAO.update(event);
        return true;
    }

    public boolean deleteEvent(EventInterface event, UserInterface user) {
        return eventDAO.delete(event.getId());
    }

    public EventInterface getEventById(UUID id) {
        return eventDAO.findById(id);
    }
}
