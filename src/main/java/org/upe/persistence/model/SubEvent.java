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
    private boolean isCertified;

    @ManyToMany(mappedBy = "subEventAttendeeOn")
    private List<User> subEventAttendeesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parentEvent_id")
    protected Event parentEvent;

    @OneToMany(mappedBy = "parentSubEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subEvent_id")
    private List<Certificate> certificadoList = new ArrayList<>();

    public SubEvent(String name, String description, LocalDate beginDate, LocalDate endDate, EventInterface parentEvent) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.parentEvent = (Event) parentEvent;
    }

    public SubEvent() {}

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

    public void addCertificate(Certificate certificate) {
        this.certificadoList.add(certificate);
    }

    public void removeCertificate(Certificate certificate) {
        this.certificadoList.remove(certificate);
    }

    public static class SubEventBuilder {
        private String name;
        private String description;
        private LocalDate beginDate;
        private LocalDate endDate;
        private EventInterface parentEvent;
        private boolean isCertified;

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

        public SubEventBuilder withCertifiedStatus(boolean isCertified) {
            this.isCertified = isCertified;
            return this;
        }

        public SubEvent build() {
            SubEvent subEvent = new SubEvent(name, description, beginDate, endDate, parentEvent);
            subEvent.setCertified(isCertified);
            return subEvent;
        }
    }

    public static SubEventBuilder Builder() {
        return new SubEventBuilder();
    }
}
