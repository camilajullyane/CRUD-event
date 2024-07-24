package org.upe.persistence;

import java.util.UUID;
import java.util.Date;
import java.time.LocalTime;

public class Event implements EventInterface{
    private String ownerCPF;
    private String id;
    private String name;
    private Date date;
    private LocalTime hour;
    private String local;
    private String organization;
    private String description;
    private String articleList;
    private String attendeesList;

    public Event(String id, String ownerCPF, String name, Date date, LocalTime hour, String local, String organization,
                 String description,
                 String articleList, String attendeesList) {
        this.ownerCPF = ownerCPF;
        this.id = id;
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.local = local;
        this.organization = organization;
        this.description = description;
        this.articleList = articleList;
        this.attendeesList = attendeesList;
    }

    // Métodos estáticos
    public static String generateID() {
        UUID uuID = UUID.randomUUID();
        String idString = uuID.toString();
        System.out.println(idString);
        return idString;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return date;
    }

    public LocalTime getHour(){
        return hour;
    }

    public void setHour(LocalTime hour){
        this.hour = hour;
    }

    public void setData(Date data) {
        this.date = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getArticleList() {
        return this.articleList.split("#");
    }

    public String[] getAttendeesList() {
        return this.attendeesList.split("#");
    }
}