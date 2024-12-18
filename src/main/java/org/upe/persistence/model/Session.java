package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Session implements SessionInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDate date;
    private LocalDateTime beginHour;
    private LocalDateTime endHour;
    private String local;
    private String description;
    private String speaker;
    private boolean privateSession;
    @ManyToOne
    @JoinColumn(name = "parentSubEvent_id")
    SubEvent parentSubEvent;

    public Session(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent) {
        this.name = name;
        this.date = date;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.local = local;
        this.description = description;
        this.speaker = speaker;
        this.parentSubEvent = (SubEvent) parentSubEvent;
    }

    public Session() {}

    public static class Builder {
        private String name;
        private LocalDate date;
        private LocalDateTime beginHour;
        private LocalDateTime endHour;
        private String local;
        private String description;
        private String speaker;
        private SubEventInterface parentSubEvent;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withBeginHour(LocalDateTime beginHour) {
            this.beginHour = beginHour;
            return this;
        }

        public Builder withEndHour(LocalDateTime endHour) {
            this.endHour = endHour;
            return this;
        }

        public Builder withLocal(String local) {
            this.local = local;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withSpeaker(String speaker) {
            this.speaker = speaker;
            return this;
        }

        public Builder withParentSubEvent(SubEventInterface parentSubEvent) {
            this.parentSubEvent = parentSubEvent;
            return this;
        }

        public Session build() {
            return new Session(name, date, beginHour, endHour, local, description, speaker, parentSubEvent);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
