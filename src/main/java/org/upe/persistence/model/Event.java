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
@Getter
@Setter
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
    private boolean privateEvent;
    @ManyToMany(mappedBy = "attendeeOn", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> attendeesList = new ArrayList<>();
    @OneToMany(mappedBy = "parentEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubEvent> subEvents = new ArrayList<>();
    @ManyToMany(mappedBy = "submittedIn")
    private List<Article> articles = new ArrayList<>();

    // Construtor privado para garantir o uso do UserBuilder
    private Event(String name, String description, LocalDate beginDate, LocalDate endDate, String local, String organization , User owner) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.owner = owner;
        this.local = local;
        this.organization = organization;
    }

    // Construtor padrão
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
        user.getAttendeeOn().remove(this);
    }

    public void addArticleOnEvent(ArticleInterface article) {
        this.articles.add((Article) article);
    }

    // UserBuilder interno
    public static class EventBuilder {
        private String name;
        private String description;
        private LocalDate beginDate;
        private LocalDate endDate;
        private String local;
        private String organization;
        private User owner;

        public EventBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EventBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventBuilder withBeginDate(LocalDate beginDate) {
            this.beginDate = beginDate;
            return this;
        }

        public EventBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public EventBuilder withLocal(String local) {
            this.local = local;
            return this;
        }

        public EventBuilder withOrganization(String organization) {
            this.organization = organization;
            return this;
        }

        public EventBuilder withOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Event build() {
            return new Event(name, description, beginDate, endDate, local, organization, owner);
        }
    }

    public static EventBuilder Builder() {
        return new EventBuilder();
    }
}
