package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.utils.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf", "email"}))
@Getter @Setter
public class User implements UserInterface {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    @ManyToMany
    @JoinTable(
            name = "event_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> attendeeOn = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "sub_event_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_event_id")
    )
    private List<SubEvent> subEventAttendeeOn = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Event> ownerOf = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Certificate> certificateList = new ArrayList<>();

    public User() {}

    public User(String name, String cpf, String email, String password) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }



    public List<EventInterface> getAttendeeOn() {
        return new ArrayList<>(attendeeOn);
    }

    public List<EventInterface> getOwnerOf() {
        return new ArrayList<>(ownerOf);
    }

    public List<ArticleInterface> getArticles() {
        return new ArrayList<>(articles);
    }

    public void subscribeToEvent(EventInterface event) {
        this.attendeeOn.add((Event) event);
    }

    public void subscribeToSubEvent(SubEventInterface subEvent) {
        this.subEventAttendeeOn.add((SubEvent) subEvent);
    }

    public void unsubscribeToSubEvent(SubEventInterface subEvent) {
        this.subEventAttendeeOn.remove((SubEvent) subEvent);
    }

    public void unsubscribeToEvent(EventInterface event) {
        this.attendeeOn.remove((Event) event);
    }

    public void addMyEventAsOwner(EventInterface event) {
        this.ownerOf.add((Event) event);
    }

    public void addArticle(ArticleInterface article) {
        this.articles.add((Article) article);
    }

    // Implementação do padrão UserBuilder
    public static class UserBuilder {
        private String name;
        private String cpf;
        private String email;
        private String password;

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = PasswordUtil.encodePassword(password);
            return this;
        }

        public User build() {
            return new User(name, cpf, email, password);
        }
    }

    public static UserBuilder Builder() {
        return new UserBuilder();
    }
}
