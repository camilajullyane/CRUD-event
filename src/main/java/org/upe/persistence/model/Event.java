package org.upe.persistence.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.ArrayList;
import java.util.Date;
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
    private Date date;
    private String local;
    private String organization;
    private String description;
    @ManyToMany(mappedBy = "events")
    private List<User> attendeesList = new ArrayList<>();
    @OneToMany(mappedBy = "parentEvent")
    private List<SubEvent> subEvents = new ArrayList<>();

    public Event(String name, String description, String date, UserInterface user) {
        this.name = name;
        this.description = description;
        this.date = new Date();
        this.owner = (User) user;
    }

    public Event() {}

    @Override
    public String[] getArticleList() {
        return new String[0];
    }

    @Override
    public String getOwnerCpf() {
        return "";
    }
}