//package org.upe.persistence;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
//public class SubEventUtility {
//    private static final String CSV_FILE_PATH = "DB/subEvent.csv";
//    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
//
//    // Create
//    public boolean addSubEvent(Event event) {
//        ArrayList<Event> events = getAllEvents();
//        event.id = Event.generateID(); // Define a new unique ID
//        events.add(event);
//        return saveEvents(events);
//    }
//}
