package org.upe.persistence.model;

import org.upe.persistence.interfaces.ArticleInterface;

public class Article implements ArticleInterface {
    private String name;
    private String articleID;
    private String userCPF;
    private String articleAbstract;

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

    public String toString(int position) {
        return "Artigo["+ position+"] - " +
                "Título: "+ this.name +
                " | Resumo: " + this.articleAbstract;

    }
}
