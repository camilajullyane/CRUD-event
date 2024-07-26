package org.upe.persistence;

import java.io.*;
import java.util.ArrayList;

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

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAttendeeOn() {
        return this.attendeeOn.split("#");
    }

    public String[] getOwnerOf() {
        return this.ownerOf.split("#");
    }

    public String[] getArticleID() {
        return this.articleID.split("#");
    }

}

