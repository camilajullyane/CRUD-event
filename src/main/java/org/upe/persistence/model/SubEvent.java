package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subEvent")
@Getter @Setter
public class SubEvent implements SubEventInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private LocalDate beginDate;
    private LocalDate endDate;
    private boolean privateSubEvent;
    @ManyToMany(mappedBy = "subEventAttendeeOn")
    private List<User> subEventAttendeesList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parentEvent_id")
    protected Event parentEvent;

    @OneToMany(mappedBy = "parentSubEvent")
    private List<Session> sessions = new ArrayList<>();


    public SubEvent(String name,String description, LocalDate beginDate,LocalDate endDate, EventInterface parentEvent) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.parentEvent = (Event) parentEvent;
    }


    public List<UserInterface> getSubEventAttendeesList() {
        return new ArrayList<>(subEventAttendeesList);
    }

    public void addAttendeeOnSubEvent(UserInterface user) {
        this.subEventAttendeesList.add((User) user);
    }

    public void removeAttendeeOnSubEvent(UserInterface user) {
        this.subEventAttendeesList.remove((User) user);
    }

    public List<SessionInterface> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    public SubEvent() {}

    // Implementação do padrão Builder
    public static class Builder {
        private String name;
        private String description;
        private LocalDate beginDate;
        private EventInterface parentEvent;
        private  LocalDate endDate;

        public Builder() {}

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withBeginDate(LocalDate date) {
            this.beginDate = date;
            return this;
        }

        public Builder withEndDate(LocalDate date) {
            this.endDate = date;
            return this;
        }

        public Builder withParentEvent(EventInterface parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        public SubEvent build() {
            return new SubEvent(name, description, beginDate, endDate,parentEvent);
        }
    }
}
