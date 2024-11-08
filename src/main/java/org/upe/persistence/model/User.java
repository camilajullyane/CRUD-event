package org.upe.persistence.model;

import jakarta.persistence.*;
import org.upe.persistence.interfaces.UserInterface;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf", "email"}))
public class User implements UserInterface {
    @Id @GeneratedValue
    private Long id;
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

    public User(String nome, String cpf, String email, String password) {
        this.name = nome;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    public User() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCPF() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
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