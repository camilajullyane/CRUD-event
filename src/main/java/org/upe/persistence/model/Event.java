package org.upe.persistence.model;

import org.upe.persistence.interfaces.EventInterface;

import java.util.UUID;

public class Event implements EventInterface {
    protected String ownerCPF;
    protected String id;
    protected String name;
    protected String date;
    protected String local;
    protected String organization;
    protected String description;
    protected String articleList;
    protected String attendeesList;

    public Event(String id, String ownerCPF, String name, String date, String local, String organization,
                 String description, String attendeesList, String articleList) {
        this.ownerCPF = ownerCPF;
        this.id = id;
        this.name = name;
        this.date = date;
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

    public void setId(String id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getOwnerCPF() {
        return this.ownerCPF;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {this.date = date;}

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {this.local = local;}

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {this.organization = organization;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;}

    public String[] getArticleList() {
        return this.articleList.split("#");
    }

    public void setArticleList(String[] articleList) {
        this.articleList = articleList;
    }

    public String[] getAttendeesList() {
        return this.attendeesList.split("#");
    }

    public void setAttendeesList(String attendeesList) {this.attendeesList = attendeesList;}

    public String toString() {
        return "Nome: " + this.name +
                " | Data: " + this.date +
                " | Local: " + this.local +
                " | Descrição: " + this.description;
    }

    public String toString(int position) {
        return "Evento["+ position+"] - " +
                "Nome: "+ this.name +
                " | Data: " + this.date +
                " | Local: " + this.local +
                " | Descrição: " + this.description;
    }
}