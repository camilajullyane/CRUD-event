package org.upe.persistence.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String articleAbstract;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Getters
    public String getTitle() {
        return this.title;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getArticleAbstract() {return articleAbstract;}

    //Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setArticleAbstract(String articleAbstract) {this.articleAbstract = articleAbstract;}

}
