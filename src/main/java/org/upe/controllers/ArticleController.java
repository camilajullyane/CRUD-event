package org.upe.controllers;

import org.upe.persistence.*;

import java.util.ArrayList;

public interface ArticleController {

    static ArticleInterface createArticle(UserInterface user, String name, String articleAbstract) {
        ArticleInterface article = ArticleUtility.createArticle(name, user.getCPF(), articleAbstract);

        UserUtility.addUserArticle(user.getCPF(), article.getArticleID());
        return article;
    }


    static ArrayList<ArticleInterface> getAllArticlesByUser(String userCPF) {
        ArrayList<ArticleInterface> articles = ArticleUtility.getAllArticlesByUser(userCPF);
        return articles;
    }


    static boolean submitArticle(ArticleInterface article, EventInterface event) {
       for (String articleID : event.getArticleList()) {
           if (articleID.equals(event.getId())) {
               return false;
           }
       }
        return EventUtility.addArticleOnList(article.getArticleID(), event.getId());
    }

}
