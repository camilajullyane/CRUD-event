package org.upe.controllers;

import org.upe.persistence.Event;
import org.upe.persistence.EventInterface;
import org.upe.persistence.EventUtility;
import org.upe.persistence.UserInterface;

import java.time.LocalTime;
import java.util.Date;
import java.util.ArrayList;

public interface EventController {
    static EventInterface createEvent(UserInterface user, String name, String description, Date date, String local,
                                      String organization) {
        EventInterface event = EventUtility.createEvent(user.getCPF(), name, date, local, organization, description);
        return null;
    }

    static ArrayList<EventInterface> showAllEvents() {
        ArrayList<Event> events = EventUtility.getAllEvents();
        return new ArrayList<EventInterface>(events);
    }


}
