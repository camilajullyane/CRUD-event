package org.upe.persistence.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String title;
    private Date date;
    private String local;
    private String organization;
    private String description;
    @ManyToMany(mappedBy = "events")
    private List<User> attendeesList = new ArrayList<>();
    @OneToMany(mappedBy = "parentEvent")
    private List<SubEvent> subEvents = new ArrayList<>();

    //Getters
    public UUID getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Date getDate(){
        return date;
    }

    public String getLocal() {
        return local;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganization() {
        return organization;
    }

    //Setters

    public void setTitle(String title){
        this.title = title;
    }

    public void setLocal(String local){
        this.local = local;
    }

    public void setOrganization(String organization){
        this.organization = organization;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
