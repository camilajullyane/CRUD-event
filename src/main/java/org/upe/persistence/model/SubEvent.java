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

    // Implementação do padrão Builder
    public static class Builder {
        private String name;
        private String speakers;
        private String description;
        private LocalDate date;
        private EventInterface parentEvent;

        public Builder() {}

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSpeakers(String speakers) {
            this.speakers = speakers;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withParentEvent(EventInterface parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        public SubEvent build() {
            return new SubEvent(name, speakers, description, date, parentEvent);
        }
    }
}
