//package org.upe.persistence;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//    public class SubEventUtility {
//        protected static String CSV_FILE_PATH = "DB/subEvent.csv";
//        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
//
//        public static boolean addSubEvent(SubEvent subEvent) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            subEvent.id = generateSubEventID();
//            subEvents.add(subEvent);
//            return saveSubEvents(subEvents);
//        }
//
//
//        public static ArrayList<SubEvent> getAllSubEvents() {
//            ArrayList<SubEvent> subEvents = new ArrayList<>();
//            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
//                String line;
//                reader.readLine();
//                while ((line = reader.readLine()) != null) {
//                    String[] values = line.split(",", -1);
//                    String id = values[0];
//                    String name = values[1];
//                    String date = values[2];
//                    String hour = values[3];
//                    String local = values[4];
//                    String organization = values[5];
//                    String description = values[6];
//                    String parentEventID = values[7];
//                    String subEventType = values[8];
//                    String articleList = values[9];
//                    String speaker = values[10];
//
//                    SubEvent subEvent = new SubEvent(id, name, date, hour, local, organization, description, parentEventID,subEventType, articleList, speaker);
//                    subEvents.add(subEvent);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return subEvents;
//        }
//
//        public static EventInterface createSubEvent(String ownerCPF, String parentEventID, String name, String date, String hour, String local,
//                                                 String organization, String description, String speaker) {
//            ArrayList<SubEvent> subEvents = SubEventUtility.getAllSubEvents();
//            String id = SubEventUtility.generateSubEventID();
//            SubEvent newSubEvent = new SubEvent(id, name, date, hour, local, organization, description, parentEventID,"", speaker);
//            subEvents.add(newSubEvent);
//            SubEventUtility.saveSubEvents(subEvents);
//            UserUtility.addOwnerOnEvent(ownerCPF, id);
//            return newSubEvent;
//        }
//
//
//        public static SubEvent getSubEventById(String id) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(id)) {
//                    return subEvent;
//                }
//            }
//            return null;
//        }
//
//        public static ArrayList<SubEvent> getSubEventByEvent(String parentEventID) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            ArrayList<SubEvent> filteredSubEvents = new ArrayList<>();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getParentEventId().equals(parentEventID)) {
//                    filteredSubEvents.add(subEvent);
//                }
//            }
//            return filteredSubEvents;
//        }
//
//        // Update
//        public static boolean updateSubEvent(SubEvent updatedSubEvent) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (int i = 0; i < subEvents.size(); i++) {
//                if (subEvents.get(i).getId().equals(updatedSubEvent.getId())) {
//                    subEvents.set(i, updatedSubEvent);
//                    return saveSubEvents(subEvents);
//                }
//            }
//            return false;
//        }
//
//        public static boolean updateSubEventName(String id, String newName) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(id)) {
//                    subEvent.name = newName;
//                    saveSubEvents(subEvents);
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        public static boolean updateSubEventDate(String id, String newDate) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(id)) {
//                    subEvent.date = newDate;
//                    saveSubEvents(subEvents);
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        public static boolean updateSubEventLocal(String id, String newLocal) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(id)) {
//                    subEvent.local = newLocal;
//                    saveSubEvents(subEvents);
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        public static boolean updateSubEventDescription(String id, String newDescription) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(id)) {
//                    subEvent.description = newDescription;
//                    saveSubEvents(subEvents);
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        // Delete
//        public static boolean deleteSubEvent(String id) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (int i = 0; i < subEvents.size(); i++) {
//                if (subEvents.get(i).getId().equals(id)) {
//                    subEvents.remove(i);
//                    return saveSubEvents(subEvents);
//                }
//            }
//            return false;
//        }
//
//        // Utility Methods
//        public static void addAttendeeOnList(String CPF, String subEventID) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(subEventID)) {
//                    subEvent.attendeesList += subEvent.attendeesList.isEmpty() ? CPF : "#" + CPF;
//                    break;
//                }
//            }
//            saveSubEvents(subEvents);
//        }
//
//        public static void deleteAttendeeOnList(String CPF, String subEventID) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(subEventID)) {
//                    String newString = "";
//                    for (int i = 0; i < subEvent.getAttendeesList().length; i++) {
//                        String attendeeCPF = subEvent.getAttendeesList()[i];
//                        if (!attendeeCPF.equals(CPF)) {
//                            if (!newString.isEmpty()) {
//                                newString += "#";
//                            }
//                            newString += attendeeCPF;
//                        }
//                    }
//                    subEvent.attendeesList = newString;
//                }
//            }
//            saveSubEvents(subEvents);
//        }
//
//        private static boolean saveSubEvents(ArrayList<SubEvent> SubEvents) {
//            try {
//                BufferedWriter write = new BufferedWriter(new FileWriter(CSV_FILE_PATH));
//                write.write("id,parentEventID,name,date,local,description,attendeesList\n");
//                for (SubEvent subEvent : SubEvents) {
//                    String line = String.format("%s,%s,%s,%s,%s,%s,%s\n",
//                            subEvent.id,
//                            subEvent.parentEventID,
//                            subEvent.name,
//                            subEvent.date,
//                            subEvent.local,
//                            subEvent.description,
//                            subEvent.attendeesList);
//                    write.write(line);
//                }
//                write.close();
//                return true;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        public static String generateSubEventID() {
//            UUID uuid = UUID.randomUUID();
//            String uuidString = uuid.toString();
//
//            while (getSubEventById(uuidString) != null) {
//                uuidString = UUID.randomUUID().toString();
//            }
//            return uuidString;
//        }
//
//        // Atualiza o palestrante de um sub-evento
//        public static boolean updateSubEventSpeaker(String id, String newSpeaker) {
//            ArrayList<SubEvent> subEvents = getAllSubEvents();
//            for (SubEvent subEvent : subEvents) {
//                if (subEvent.getId().equals(id)) {
//                    subEvent.setSpeakers(newSpeaker);
//                    saveSubEvents(subEvents);
//                    return true;
//                }
//            }
//            return false;
//        }
//
//    }