package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.SubEventInterface;

import java.util.UUID;

@Entity
@Table(name = "subEvent")
@Getter @Setter
public class SubEvent implements SubEventInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    protected String speakers;
    protected String hour;
    protected String description;
    @ManyToOne
    @JoinColumn(name = "parentEvent_id")
    protected Event parentEvent;

    public SubEvent(String speakers, String hour, Event parentEvent) {
        this.speakers = speakers;
        this.hour = hour;
        this.parentEvent = parentEvent;
    }

    public SubEvent() {}
}
