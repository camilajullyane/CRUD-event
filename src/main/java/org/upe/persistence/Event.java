package org.upe.persistence;

import java.text.ParseException;
import java.util.Locale;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Event implements EventInterface{
//    public String getCPF;
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
                 String description,
                 String articleList, String attendeesList) {
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

    public String getName() {
        return name;
    }

    public String getOwnerCPF() {
        return this.ownerCPF;
    }

    public String getDate() {
        return date;
    }

    public String getLocal() {
        return local;
    }

    public String getOrganization() {
        return organization;
    }

    public String getDescription() {
        return description;
    }

    public String[] getArticleList() {
        return this.articleList.split("#");
    }

    public String[] getAttendeesList() {
        return this.attendeesList.split("#");
    }

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