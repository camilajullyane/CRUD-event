package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.upe.persistence.interfaces.UserInterface;

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
            name = "events_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events = new ArrayList<>();
    @OneToMany
    private List<Event> eventsOwnered;
    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    public User() {}

    public User(String name, String cpf, String email, String password) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    @Override
    public String[] getAttendeeOn() {
        return new String[0];
    }

    @Override
    public String[] getOwnerOf() {
        return new String[0];
    }
}