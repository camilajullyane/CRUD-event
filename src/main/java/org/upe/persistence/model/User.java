package org.upe.persistence.model;

import org.upe.persistence.interfaces.UserInterface;

//User user = new User(fudiufgasdf) - user.
//UserInterface user = new User(shiufdas) user.

public class User implements UserInterface {
    protected String name;
    protected String CPF;
    protected String email;
    protected String attendeeOn;
    protected String ownerOf;
    protected String articleID;


    public User(String name ,String email, String CPF, String attendeeOn, String ownerOf, String articleID) {
        this.name = name;
        this.email = email;
        this.CPF = CPF;
        this.attendeeOn = attendeeOn;
        this.ownerOf = ownerOf;
        this.articleID = articleID;
    }

    public String getCPF() {
        return this.CPF;
    }

    public void setCPF(String CPF) {this.CPF = CPF;}

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
        this.attendeeOn = this.attendeeOn.isEmpty() ? eventID : "#" + eventID;
    }

    public void deleteAttendeeOn(String eventID) {
        String newString = "";
        for (int i = 0; i < this.getAttendeeOn().length; i++) {
            String id = this.getAttendeeOn()[i];
            if (!id.equals(eventID)) {
                if (!newString.isEmpty()) {
                    newString += "#";
                }
                newString += id;
            }
            this.attendeeOn = newString;
        }
    }

    public String[] getOwnerOf() {
        return this.ownerOf.split("#");
    }

    public void addOwnerOf(String eventID) {
        this.ownerOf = this.ownerOf.isEmpty() ? eventID : "#" + eventID;
    }

    public void deleteOwnerOf(String eventID) {
        String newString = "";
        for (int i = 0; i < this.getOwnerOf().length; i++) {
            String id = this.getOwnerOf()[i];
            if (!id.equals(eventID)) {
                if (!newString.isEmpty()) {
                    newString += "#";
                }
                newString += id;
            }
        }
        this.ownerOf = newString;
    }

    public String[] getArticleID() {
        return this.articleID.split("#");
    }

    public void addArticleID(String articleID) {
        this.articleID = this.articleID.isEmpty() ? articleID : "#" + articleID;
    }
}

