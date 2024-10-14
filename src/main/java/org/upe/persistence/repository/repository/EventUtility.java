package org.upe.persistence.repository.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.upe.persistence.repository.model.Event;
import org.upe.persistence.repository.interfaces.EventInterface;

public class EventUtility {
    private static final UserUtility userUtility = new UserUtility();

    private static final Logger LOGGER = Logger.getLogger(EventUtility.class.getName());
    protected static String csvFilePath = "DB/event.csv";
  
    private EventUtility() {
        throw new UnsupportedOperationException("Essa é uma utilityClass e não pode ser instânciada");
    }

    public static void setCsvFilePath(String csvFilePath) {
        EventUtility.csvFilePath = csvFilePath;
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
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
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo CSV", e);
        }
        return events;
    }

    public static  List<Event> getAllEventsByUser(String ownerCPF) {
        List<Event> allEvents = getAllEvents();
        ArrayList<Event> userEvents = new ArrayList<>();

        for (Event event : allEvents) {
            if (event.getOwnerCPF().equals(ownerCPF)) {
                userEvents.add(event);
            }
        }
        return userEvents;
    }

    public static List<Event> getEventsIn(String cpf) {
        List<Event> allEvents = getAllEvents();
        ArrayList<Event> eventsIn = new ArrayList<>();

        for (Event event : allEvents) {
            String[] attendees = event.getAttendeesList();
            for (String attendee : attendees) {
                if (attendee.trim().equals(cpf)) {
                    eventsIn.add(event);
                    break;
                }
            }
        }
        return eventsIn;
    }

    public static EventInterface createEvent(String ownerCPF, String name, String date, String local,
                                             String organization, String description) {
        List<Event> events = EventUtility.getAllEvents();
        String id = EventUtility.generateEventID();
        Event newEvent = new Event(id, ownerCPF, name, date, local, organization, description, "", "");
        events.add(newEvent);
        EventUtility.saveEvents(events);
        userUtility.addOwnerOnEvent(ownerCPF, id);
        return newEvent;
    }

    public static Event getEventById(String id) {
        List<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public static boolean updateEventLocal(String id, String newLocal) {
        List<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setLocal(newLocal);
                return saveEvents(events);
            }
        }
        return false;
    }

    public static boolean updateEventName(String id, String newName) {
        List<Event> events = getAllEvents();
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
        List<Event> events = getAllEvents();
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
        List<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setOrganization(newOrganization);
                return saveEvents(events);
            }
        }
        return false;
    }

    public static boolean updateEventDate(String id, String newDate) {
        List<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setDate(newDate);
                return saveEvents(events);
            }
        }
        return false;
    }

    // Delete
    public static boolean deleteEvent(String id) {
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
    public static void addAttendeeOnList(String userCPF, String eventID) {
        List<Event> events = getAllEvents();

        for(Event event : events) {
            if (event.getId().equals(eventID)) {
                event.addAttendeesList(userCPF);
                break;
            }
        }
        saveEvents(events);
    }

    public static void deleteAttendeeOnList(String userCPF, String eventID) {
        List<Event> events = getAllEvents();

        for(Event event : events) {
            if(event.getId().equals(eventID)) {

                event.deleteAttendee(userCPF);
            }
        }
        saveEvents(events);
    }


    public static boolean saveEvents(List<Event> events) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(csvFilePath))) {
            write.write("id,ownerCPF,name,date,local,organization,description,attendeesList,articleList\n");
            for (Event event : events) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%n", event.getId(),
                        event.getOwnerCPF(),
                        event.getName(),
                        event.getDate(),
                        event.getLocal(),
                        event.getOrganization(),
                        event.getDescription(),
                        String.join("#", event.getAttendeesList()),
                        String.join("#", event.getArticleList()));
                write.write(line);
            }
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo CSV", e);
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
        List<Event> events = getAllEvents();

        for(Event event : events) {
            if (event.getId().equals(eventID)) {
                event.addArticleList(articleID);
                break;
            }
        }
        saveEvents(events);
        return true;
    }
}
