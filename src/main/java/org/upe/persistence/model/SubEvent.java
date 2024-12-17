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
    private boolean isCertified;
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

    @OneToMany(mappedBy = "subEvent")
    private List<Certificate> certificates = new ArrayList<>();


    public SubEvent(String name, String description, LocalDate beginDate, LocalDate endDate, EventInterface parentEvent) {
        this.isCertified = false;
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

    // Adicionar Interface
    public List<Certificate> getCertificates() { return new ArrayList<>(certificates);}



    public SubEvent() {}

    // Implementação do padrão UserBuilder
    public static class SubEventBuilder {
        private String name;
        private String description;
        private LocalDate beginDate;
        private EventInterface parentEvent;
        private  LocalDate endDate;

        public SubEventBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SubEventBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SubEventBuilder withBeginDate(LocalDate date) {
            this.beginDate = date;
            return this;
        }

        public SubEventBuilder withEndDate(LocalDate date) {
            this.endDate = date;
            return this;
        }

        public SubEventBuilder withParentEvent(EventInterface parentEvent) {
            this.parentEvent = parentEvent;
            return this;
        }

        public SubEvent build() {
            return new SubEvent(name, description, beginDate, endDate,parentEvent);
        }
    }

    public static SubEventBuilder Builder() {
        return new SubEventBuilder();
    }
}
