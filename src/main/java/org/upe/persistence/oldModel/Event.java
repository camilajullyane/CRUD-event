package org.upe.persistence.oldModel;

import org.upe.persistence.interfaces.EventInterface;

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

    public String getOwnerCPF() {
        return this.ownerCPF;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void addArticleList(String articleID) {
        this.articleList = this.articleList.isEmpty() ? articleID : this.attendeesList + "#" + articleID;
    }

    public String[] getAttendeesList() {
        return this.attendeesList.split("#");
    }

    public void addAttendeesList(String userCPF) {
        this.attendeesList = this.attendeesList.isEmpty() ? userCPF : this.attendeesList + "#" + userCPF;
    }

    public void deleteAttendee(String userCPF) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < this.getAttendeesList().length; i++) {
            String cpf = this.getAttendeesList()[i];
            if (!cpf.equals(userCPF)) {
                if (!newString.isEmpty()) {
                    newString.append("#");
                }
                newString.append(id);
            }
        }
        this.attendeesList = newString.toString();
    }
}