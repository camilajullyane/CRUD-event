package org.upe.persistence;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.upe.persistence.User;

public class EventUtility {
    private static final String CSV_FILE_PATH = "DB/event.csv";
    private static final String[] HEADER = {"id", "ownerCPF","name", "date", "local", "organization", "description", "attendeesList"};
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // Create
    public boolean addEvent(Event event) {
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
                String articleList = values[7] == null ? "" : values[7];
                String attendeeList = values[8] == null ? "" : values[8];
                Event event = new Event(id,ownerCPF,name, date, local,organization, description, articleList,
                        attendeeList);
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

    public static EventInterface createEvent(String ownerCPF, String name, String date, String local,
                                             String organization, String description) {
        String id = EventUtility.generateEventID();

        try {
            String newLine = String.format("%s,%s,%s,%s,%s,%s,%s,,", id, ownerCPF, name, date, local, organization, description);
            FileWriter writer = new FileWriter(CSV_FILE_PATH, true);
            writer.append(System.lineSeparator());
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserUtility.addOwnerOnEvent(ownerCPF, id);
        return new Event(id, ownerCPF, name, date, local, organization, description, "", "");
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
        return false;
    }


    public static void updateEventName(String id, String newName) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.name = newName;
                saveEvents(events);
            }
        }
//        return false;
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
        return false;
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
        return false;
    }

    // Delete
    public static boolean deleteEvent(String id) {
        ArrayList<Event> events = getAllEvents();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                events.remove(i);
                return saveEvents(events);
            }
        }
        return false;
    }

    // Utility Methods
    private static boolean saveEvents(List<Event> events) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write header
            writer.write(String.join(",", HEADER));
            writer.newLine();

            // Write event data
            for (Event event : events) {
                String[] data = {
                        event.getId(),
                        event.getName(),
                        event.getDate(),
                        event.getLocal(),
                        event.getOrganization(),
                        event.getDescription(),
                };
                writer.write(String.join(",", data));
                writer.newLine();
            }
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

}
