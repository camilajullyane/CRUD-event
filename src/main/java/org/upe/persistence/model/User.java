package org.upe.persistence.model;

import org.upe.persistence.interfaces.UserInterface;

public class User implements UserInterface {
    protected String name;
    protected String userCPF;
    protected String password;
    protected String email;
    protected String attendeeOn;
    protected String ownerOf;
    protected String articleID;

    public User(String name, String email, String userCPF, String password, String attendeeOn, String ownerOf, String articleID) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.userCPF = userCPF;
        this.attendeeOn = attendeeOn;
        this.ownerOf = ownerOf;
        this.articleID = articleID;
    }

    public String getCPF() {
        return this.userCPF;
    }

    public String getPassword() {
        return this.password;
    }

    public void setCPF(String userCPF) {this.userCPF = userCPF;}

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {this.email = email;}

    public String getName() {
        return this.name;
    }

    public String[] getAttendeeOn() {
        return this.attendeeOn.split("#");
    }

    public void addAttendeeOn(String eventID) {
        this.attendeeOn = this.attendeeOn.isEmpty() ? eventID : this.attendeeOn + "#" + eventID;
    }

    public void deleteAttendeeOn(String eventID) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < this.getAttendeeOn().length; i++) {
            String id = this.getAttendeeOn()[i];
            if (!id.equals(eventID)) {
                if (!newString.isEmpty()) {
                    newString.append("#");
                }
                newString.append(id);
            }
            this.attendeeOn = newString.toString();
        }
    }

    public String[] getOwnerOf() {

        return this.ownerOf.split("#");
    }

    public void addOwnerOf(String eventID) {
        this.ownerOf = this.ownerOf.isEmpty() ? eventID : this.attendeeOn + "#" + eventID;
    }

    public void deleteOwnerOf(String eventID) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < this.getOwnerOf().length; i++) {
            String id = this.getOwnerOf()[i];
            if (!id.equals(eventID)) {
                if (!newString.isEmpty()) {
                    newString.append("#");
                }
                newString.append(id);
            }
        }
        this.ownerOf = newString.toString();
    }

    public String[] getArticleID() {
        return this.articleID.split("#");
    }

    public void addArticleID(String articleID) {
        this.articleID = this.articleID.isEmpty() ? articleID : this.attendeeOn + "#" + articleID;
    }
}

