package org.upe.persistence;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventUtility {
    private static final String CSV_FILE_PATH = "DB/event.csv";
    private static final String[] HEADER = {"id", "name", "date", "local", "organization", "description"};
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // Create
    public boolean addEvent(Event event) {
        List<Event> events = getAllEvents();
        event.setId(Event.generateID()); // Define a new unique ID
        events.add(event);
        return saveEvents(events);
    }

    // Read
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
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
                String name = values[1];
                Date date = DATE_FORMAT.parse(values[2]);
                String local = values[3];
                String organization = values[4];
                String description = values[5];
                Event event = new Event(id, name, date, local, organization, description);
                events.add(event);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getEventById(String id) {
        List<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    // Update
    public boolean updateEvent(Event updatedEvent) {
        List<Event> events = getAllEvents();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(updatedEvent.getId())) {
                events.set(i, updatedEvent);
                return saveEvents(events);
            }
        }
        return false;
    }

    // Delete
    public boolean deleteEvent(String id) {
        List<Event> events = getAllEvents();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                events.remove(i);
                return saveEvents(events);
            }
        }
        return false;
    }

    // Utility Methods
    private boolean saveEvents(List<Event> events) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write header
            writer.write(String.join(",", HEADER));
            writer.newLine();

            // Write event data
            for (Event event : events) {
                String[] data = {
                        event.getId(),
                        event.getName(),
                        DATE_FORMAT.format(event.getData()),
                        event.getLocal(),
                        event.getOrganization(),
                        event.getDescription()
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
}
