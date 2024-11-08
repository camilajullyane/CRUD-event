package org.upe.persistence.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "subEvent")
public class SubEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    protected String speakers;
    protected String hour;
    @ManyToOne
    @JoinColumn(name = "parentEvent_id")
    protected Event parentEvent;

    // Getters

    public UUID getId() {
        return this.id;
    }
    public String getSpeakers() {
        return speakers;
    }

    public Event getParentEvent() {
        return parentEvent;
    }

    public String getHour() {
        return hour;
    }

    // Setters

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public void setHour(String newHour) {
        this.hour = newHour;
    }

}
