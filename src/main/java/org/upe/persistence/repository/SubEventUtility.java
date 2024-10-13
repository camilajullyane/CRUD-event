package org.upe.persistence.repository;

import org.upe.persistence.model.SubEvent;
import org.upe.persistence.interfaces.EventInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubEventUtility {
        private static final Logger LOGGER = Logger.getLogger(SubEventUtility.class.getName());
        protected static String csvFilePath = "DB/subevent.csv";
      
        private SubEventUtility() {
            throw new UnsupportedOperationException("Essa é uma utilityClass e não pode ser instânciada");
        }

        public static void setCsvFilePath(String csvFilePath) {
            SubEventUtility.csvFilePath = csvFilePath;
        }

        public static boolean addSubEvent(SubEvent subEvent) {
            List<SubEvent> subEvents = getAllSubEvents();
            subEvent.setId(generateSubEventID());
            subEvents.add(subEvent);
            return saveSubEvents(subEvents);
        }

        public static List<SubEvent> getAllSubEvents() {
            List<SubEvent> subEvents = new ArrayList<>();

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
                    String parentID = values[1];
                    String name = values[2];
                    String date = values[3];
                    String hour = values[4];
                    String local = values[5];
                    String description = values[6];
                    String speaker = values[7];
                    String attendeesList = values[8] == null ? "" : values[8];

                    SubEvent subEvent = new SubEvent(id, parentID, name, date, hour, local,"", description, speaker,
                            attendeesList);
                    subEvents.add(subEvent);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo CSV", e);
            }
            return subEvents;
        }

        public static EventInterface createSubEvent(String parentEventID, String name, String date, String hour, String local,
                                                    String organization, String description, String speaker) {
            List<SubEvent> subEvents = SubEventUtility.getAllSubEvents();
            String id = SubEventUtility.generateSubEventID();
            SubEvent newSubEvent = new SubEvent(id, parentEventID, name, date, hour, local, organization, description, speaker, "");
            subEvents.add(newSubEvent);
            SubEventUtility.saveSubEvents(subEvents);
            return newSubEvent;
        }


        public static SubEvent getSubEventById(String id) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(id)) {
                    return subEvent;
                }
            }
            return null;
        }

        public static List<SubEvent> getSubEventByEvent(String parentEventID) {
            List<SubEvent> subEvents = getAllSubEvents();
            ArrayList<SubEvent> filteredSubEvents = new ArrayList<>();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getParentEventID().equals(parentEventID)) {
                    filteredSubEvents.add(subEvent);
                }
            }
            return filteredSubEvents;
        }

        // Update
        public static boolean updateSubEvent(SubEvent updatedSubEvent) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (int i = 0; i < subEvents.size(); i++) {
                if (subEvents.get(i).getId().equals(updatedSubEvent.getId())) {
                    subEvents.set(i, updatedSubEvent);
                    return saveSubEvents(subEvents);
                }
            }
            return false;
        }

        public static boolean updateSubEventName(String id, String newName) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(id)) {
                    subEvent.setName(newName);
                    saveSubEvents(subEvents);
                    return true;
                }
            }
            return false;
        }

        public static boolean updateSubEventDate(String id, String newDate) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(id)) {
                    subEvent.setDate(newDate);
                    saveSubEvents(subEvents);
                    return true;
                }
            }
            return false;
        }

        public static boolean updateSubEventLocal(String id, String newLocal) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(id)) {
                    subEvent.setLocal(newLocal);
                    saveSubEvents(subEvents);
                    return true;
                }
            }
            return false;
        }

        public static boolean updateSubEventDescription(String id, String newDescription) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(id)) {
                    subEvent.setDescription(newDescription);
                    saveSubEvents(subEvents);
                    return true;
                }
            }
            return false;
        }

        // Delete
        public static boolean deleteSubEvent(String id) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (int i = 0; i < subEvents.size(); i++) {
                if (subEvents.get(i).getId().equals(id)) {
                    subEvents.remove(i);
                    return saveSubEvents(subEvents);
                }
            }
            return false;
        }

        // Utility Methods
        public static void addAttendeeOnList(String userCPF, String subEventID) {
            List<SubEvent> subEvents = getAllSubEvents();

            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(subEventID)) {
                    subEvent.addAttendeesList(userCPF);
                    break;
                }
            }
            saveSubEvents(subEvents);
        }

        public static void deleteAttendeeOnList(String userCPF, String subEventID) {
            List<SubEvent> subEvents = getAllSubEvents();

            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(subEventID)) {
                    subEvent.deleteAttendee(userCPF);
                }
                saveSubEvents(subEvents);
            }
        }

        private static boolean saveSubEvents(List<SubEvent> subEvents) {
            try (BufferedWriter write = new BufferedWriter(new FileWriter(csvFilePath))) {
                write.write("id,parentEventID,name,date,hour,local,description,speaker,attendeesList\n");
                for (SubEvent subEvent : subEvents) {
                    String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                            subEvent.getId(),
                            subEvent.getParentEventID(),
                            subEvent.getName(),
                            subEvent.getDate(),
                            subEvent.getHour(),
                            subEvent.getLocal(),
                            subEvent.getDescription(),
                            subEvent.getSpeakers(),
                            String.join("#", subEvent.getAttendeesList()));
                    write.write(line);
                }
                return true;
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo CSV", e);
                return false;
            }
        }


        public static String generateSubEventID() {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();

            while (getSubEventById(uuidString) != null) {
                uuidString = UUID.randomUUID().toString();
            }
            return uuidString;
        }

        // Atualiza o palestrante de um sub-evento
        public static boolean updateSubEventSpeaker(String id, String newSpeaker) {
            List<SubEvent> subEvents = getAllSubEvents();
            for (SubEvent subEvent : subEvents) {
                if (subEvent.getId().equals(id)) {
                    subEvent.setSpeakers(newSpeaker);
                    saveSubEvents(subEvents);
                    return true;
                }
            }
            return false;
        }



    }