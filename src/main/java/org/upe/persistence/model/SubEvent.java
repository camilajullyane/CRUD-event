package org.upe.persistence.model;

import org.upe.persistence.interfaces.SubEventInterface;


public class SubEvent extends Event implements SubEventInterface {
    protected String parentEventID;
    protected String speakers;
    protected String hour;

    // Construtor
    public SubEvent(String id, String parentEventID, String name, String date, String hour, String local, String organization,
                    String description, String speakers, String attendeesList) {
        super(id, "", name, date, local, organization, description, attendeesList, "");
        this.parentEventID = parentEventID;
        this.speakers = speakers;
        this.hour = hour;
    }

    // Getters e Setters
    public String getSpeakers() {
        return this.speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public String getParentEventID() {
        return this.parentEventID;
    }

    public String getHour() {
        return this.hour;
    }
}