package org.upe.persistence;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class EventUtility {
    private static final String CSV_FILE_PATH = "DB/event.csv";
    private static final String[] HEADER = {"id", "name", "date", "local", "organization", "description"};
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Create
    public boolean addEvent(Event event) {
        ArrayList<Event> events = getAllEvents();
        event.setId(Event.generateID()); // Define a new unique ID
        events.add(event);
        return saveEvents(events);
    }

    // Read
    public static ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                String[] values = line.split(",");
                String id = values[0];
                String ownerCPF = values[1];
                String name = values[2];
                Date date = DATE_FORMAT.parse(values[3]);
                String local = values[4];
                String organization = values[5];
                String description = values[6];
                String articleList = values[7];
                String attendeeList = values[8];
                Event event = new Event(id,ownerCPF,name, date, local,organization, description, articleList,
                        attendeeList);
                events.add(event);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static EventInterface createEvent(String ownerCPF, String name, Date date, String local,
                                             String organization, String description) {
        String id = EventUtility.generateEventID();
        try {
            String newLine = String.format("%s,%s,%s,%s,%s,%s,%s,", id, ownerCPF, name, date, local, organization, description);
            FileWriter writer = new FileWriter(CSV_FILE_PATH, true);
            writer.append(System.lineSeparator());
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                event.setLocal(newLocal);
                return saveEvents(events);
            }
        }
        return false;
    }


    public static boolean updateEventName(String id, String newName) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setName(newName);
                return saveEvents(events);
            }
        }
        return false;
    }

    // Update Description
    public static boolean updateEventDescription(String id, String newDescription) {
        ArrayList<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setDescription(newDescription);
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
                event.setOrganization(newOrganization);
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
