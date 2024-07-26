package org.upe.persistence;

import java.util.List;
import java.util.UUID;


public class SubEvent extends Event implements SubEventInterface {
    protected String parentEventID;
    private String subEventType;
    private String speakers;

    // Construtor
    public SubEvent(String id, String name, String date, String hour, String local, String organization, String description, String parentEventID, String subEventType, String speakers) {
        super(id, name, date, hour, local, organization, description, "", "");
        this.parentEventID = parentEventID;
        this.subEventType = subEventType;
        this.speakers = speakers;
    }

    public static String generateID() {
        UUID uuID = UUID.randomUUID();
        String idString = uuID.toString();
        System.out.println(idString);
        return idString;
    }

    // Getters e Setters
    public String getParentEventId() {
        return this.parentEventID;
    }

    public String getSubEventType() {
        return this.subEventType;
    }

    public void setSubEventType(String subEventType) {
        this.subEventType = subEventType;
    }

    public String getSpeakers() {
        return this.speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public String getParentEventID() {
        return parentEventID;
    }
}