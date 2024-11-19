package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import java.util.Date;
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
    protected String description;
    protected Date date;
    @ManyToOne
    @JoinColumn(name = "parentEvent_id")
    protected Event parentEvent;

    public SubEvent(String name, String speakers, String description, Date date, EventInterface parentEvent) {
        this.name = name;
        this.speakers = speakers;
        this.description = description;
        this.date = date;
        this.parentEvent = (Event) parentEvent;
    }

    public SubEvent() {}
}
