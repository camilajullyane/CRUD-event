package org.upe.persistence;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.upe.persistence.User;

public class EventUtility {
    protected static String CSV_FILE_PATH = "DB/event.csv";
    // Create
    public static boolean addEvent(Event event) {
        ArrayList<Event> events = getAllEvents();
        event.id = Event.generateID(); // Define a new unique ID
        events.add(event);
        return saveEvents(events);
    }

    // Read
    public static ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);
                String id = values[0];
                String ownerCPF = values[1];
                String name = values[2];
                String date = values[3];
                String local = values[4];
                String organization = values[5];
                String description = values[6];
                String attendeeList = values[7] == null ? "" : values[7];
                String articleList = values[8] == null ? "" : values[8];

                Event event = new Event(id,ownerCPF,name, date, local,organization, description, attendeeList, articleList);
                events.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static  ArrayList<Event> getAllEventsByUser(String ownerCPF) {
        ArrayList<Event> allEvents = getAllEvents();
        ArrayList<Event> userEvents = new ArrayList<>();

        for (Event event : allEvents) {
            if (event.getOwnerCPF().equals(ownerCPF)) {
                userEvents.add(event);
            }
        }
        return userEvents;
    }

    public static ArrayList<Event> getEventsIn(String CPF) {
        ArrayList<Event> allEvents = getAllEvents();
        ArrayList<Event> eventsIn = new ArrayList<>();

        for (Event event : allEvents) {
            String[] attendees = event.getAttendeesList();
            for (String attendee : attendees) {
                if (attendee.trim().equals(CPF)) {
                    eventsIn.add(event);
                    break;
                }
            }
        }
        return eventsIn;
    }

    public static EventInterface createEvent(String ownerCPF, String name, String date, String local,
                                             String organization, String description) {
        ArrayList<Event> events = EventUtility.getAllEvents();
        String id = EventUtility.generateEventID();
        Event newEvent = new Event(id, ownerCPF, name, date, local, organization, description, "", "");
        events.add(newEvent);
        EventUtility.saveEvents(events);
        UserUtility.addOwnerOnEvent(ownerCPF, id);
        return newEvent;
    }


    public static Event getEventById(String id) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    // Update
    public static boolean updateEvent(Event updatedEvent) {
        ArrayList<Event> events = getAllEvents();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(updatedEvent.getId())) {
                events.set(i, updatedEvent);
                return saveEvents(events);
            }
        }
        return false;
    }

    public static boolean updateEventLocal(String id, String newLocal) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.local = newLocal;
                return saveEvents(events);
            }
        }
        return true;
    }


    public static boolean updateEventName(String id, String newName) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.name = newName;
                saveEvents(events);
            }
        }
        return true;
    }

    // Update Description
    public static boolean updateEventDescription(String id, String newDescription) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.description = newDescription;
                return saveEvents(events);
            }
        }
        return true;
    }

    // Update Organization
    public static boolean updateEventOrganization(String id, String newOrganization) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.organization = newOrganization;
                return saveEvents(events);
            }
        }
        return true;
    }

    public static boolean updateEventDate(String id, String newDate) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.organization = newDate;
                return saveEvents(events);
            }
        }
        return true;
    }

    // Delete
    public static boolean deleteEvent(String id) {
        ArrayList<Event> events = getAllEvents();
        events.size();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                events.remove(i);
                return saveEvents(events);
            }
        }
        return false;
    }

    // Utility Methods
    public static void addAttendeeOnList(String CPF, String eventID) {
        ArrayList<Event> events = getAllEvents();

        for(Event event : events) {
            if (event.getId().equals(eventID)) {
                event.attendeesList += event.attendeesList.isEmpty() ? CPF : "#" + CPF;
                break;
            }
        }
        saveEvents(events);
    }

    public static void deleteAttendeeOnList(String CPF, String eventID) {
        ArrayList<Event> events = getAllEvents();

        for(Event event : events) {
            if(event.getId().equals(eventID)) {
                String newString = "";
                for (int i = 0; i < event.getAttendeesList().length; i++) {
                    String id = event.getAttendeesList()[i];
                    if (!id.equals(CPF)) {
                        if (!newString.isEmpty()) {
                            newString += "#";
                        }
                        newString += id;
                    }
                }
                event.attendeesList = newString;
            }
        }
        saveEvents(events);
    }


    private static boolean saveEvents(List<Event> events) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(CSV_FILE_PATH));
            write.write("id,ownerCPF,name,date,local,organization,description,attendeesList,articleList\n");
            for (Event event : events) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", event.id, event.ownerCPF, event.name, event.date,
                        event.local,
                        event.organization, event.description, event.attendeesList,event.articleList);
                write.write(line);
            }
            write.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String generateEventID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        while (getEventById(uuidString) != null) {
            uuidString = UUID.randomUUID().toString();
        }
        return uuidString;
    }


    public static boolean addArticleOnList(String articleID, String eventID) {
        ArrayList<Event> events = getAllEvents();

        for(Event event : events) {
            if (event.getId().equals(eventID)) {
                event.articleList += event.articleList.isEmpty() ? articleID : "#" + articleID;
                break;
            }
        }
        saveEvents(events);
        return true;
    }
}
