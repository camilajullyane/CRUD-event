package org.upe.persistence.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter @Setter
public class Event implements EventInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String name;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String local;
    private String organization;
    private String description;
    @ManyToMany(mappedBy = "attendeeOn")
    private List<User> attendeesList = new ArrayList<>();
    @OneToMany(mappedBy = "parentEvent")
    private List<SubEvent> subEvents = new ArrayList<>();
    @ManyToMany(mappedBy = "submittedIn")
    private List<Article> articles = new ArrayList<>();

    public Event(String name, String description, LocalDate beginDate, LocalDate endDate, String local, String organization , UserInterface user) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.owner = (User) user;
        this.local = local;
        this.organization = organization;
    }

    public Event() {}

    public List<UserInterface> getAttendeesList() {
        return new ArrayList<>(attendeesList);
    }

    public List<SubEventInterface> getSubEvents() {
        return new ArrayList<>(subEvents);
    }

    public List<ArticleInterface> getArticles() {
        return new ArrayList<>(articles);
    }

    public void addAttendeeOnEvent(UserInterface user) {
        this.attendeesList.add((User) user);
    }

    public void removeAttendeeOnEvent(UserInterface user) {
        this.attendeesList.remove((User) user);
    }
}