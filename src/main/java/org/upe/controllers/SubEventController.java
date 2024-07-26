//package org.upe.controllers;
//
//import org.upe.persistence.*;
//import org.upe.ui.MyEvents;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public interface SubEventController {
//
//    static SubEventInterface createSubEvent(UserInterface user, EventInterface event, String name, String date, String local, String hour,
//                                            String description, String organization, String speaker) {
//        return (SubEventInterface) SubEventUtility.createSubEvent(user.getCPF(), event.getId(), name, date, hour, local,organization, description,speaker);
//    }
//
//    static ArrayList<SubEventInterface> showAllSubEvents() {
//        ArrayList<SubEvent> subEvents = SubEventUtility.getAllSubEvents();
//
//        return new ArrayList<SubEventInterface>(subEvents);
//    }
//
//    static ArrayList<SubEventInterface> subEventsByEvent(String eventId) {
//        ArrayList<SubEvent> subEventsByEvent = SubEventUtility.getSubEventByEvent(eventId);
//        return new ArrayList<SubEventInterface>(subEventsByEvent);
//    }
//
//    static boolean editSubEventName(String id, String newName) {
//        SubEventUtility.updateSubEventName(id, newName);
//        return true;
//    }
//
//    static boolean editSubEventDate(String id, String newDate) {
//        SubEventUtility.updateSubEventDate(id, newDate);
//        return true;
//    }
//
//    static boolean editSubEventLocal(String id, String newLocal) {
//        SubEventUtility.updateSubEventLocal(id, newLocal);
//        return true;
//    }
//
//    static boolean editSubEventDescription(String id, String newDescription) {
//        SubEventUtility.updateSubEventDescription(id, newDescription);
//        return true;
//    }
//
//    static boolean editSubEventSpeaker(String id, String newSpeaker) {
//        SubEventUtility.updateSubEventSpeaker(id, newSpeaker);
//        return true;
//    }
//
//    static boolean deleteSubEvent(String id) {
//        SubEventUtility.deleteSubEvent(id);
//        return true;
//    }
//}
