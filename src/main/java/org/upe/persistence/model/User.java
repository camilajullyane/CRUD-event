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


    protected User(String name ,String email, String CPF, String attendeeOn, String ownerOf, String articleID) {
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

    public void setAttendeeOn(String attendeeOn) {this.attendeeOn = attendeeOn;}

    public String[] getOwnerOf() {
        return this.ownerOf.split("#");
    }

    public void setOwnerOf(String ownerOf) {this.ownerOf = ownerOf;}

    public String[] getArticleID() {
        return this.articleID.split("#");
    }

    public void setArticleID(String articleID) {this.articleID = articleID;}
}

