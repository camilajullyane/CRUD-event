package org.upe.persistence;

import java.time.LocalTime;
import java.util.List;
import java.util.Date;
import java.util.UUID;


public class SubEvent extends Event {
    private String parentEventId;
    private String subEventType;
    private List<String> speakers;

    // Construtor
    public SubEvent(String id, String name, Date date, LocalTime hour, String local, String organization, String description,
                    String articleList,
                    String parentEventId, String subEventType, List<String> speakers) {
        super(id, name, date, hour, local, organization, description, articleList);
        this.parentEventId = parentEventId;
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
        return parentEventId;
    }

    public String getSubEventType() {
        return subEventType;
    }

    public void setSubEventType(String subEventType) {
        this.subEventType = subEventType;
    }

    public List<String> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<String> speakers) {
        this.speakers = speakers;
    }

}
