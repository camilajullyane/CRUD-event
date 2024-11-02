package org.upe.persistence.oldModel;

import org.upe.persistence.interfaces.ArticleInterface;

public class Article implements ArticleInterface {
    private String name;
    private final String articleID;
    private final String userCPF;
    private final String articleAbstract;

    public Article(String name, String eventID, String userCPF, String articleAbstract) {
        this.name = name;
        this.articleID = eventID;
        this.userCPF = userCPF;
        this.articleAbstract = articleAbstract;
    }

    // Getters e setters, se necessário
    public String getName() {
        return this.name;
    }

    public String getArticleID() {
        return this.articleID;
    }

    public String getUserCPF() {
        return this.userCPF;
    }

    public String getArticleAbstract() {
        return this.articleAbstract;
    }

    public void setName(String updatedName) {
        this.name = updatedName;
    }
}
