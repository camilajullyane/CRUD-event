package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "subEvent")
@Getter @Setter
public class SubEvent implements SubEventInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String speakers;
    private String description;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "parentEvent_id")
    protected Event parentEvent;

    public SubEvent(String name, String speakers, String description, LocalDate date, EventInterface parentEvent) {
        this.name = name;
        this.speakers = speakers;
        this.description = description;
        this.date = date;
        this.parentEvent = (Event) parentEvent;
    }

    public SubEvent() {}
}
