package org.upe.controllers;

import jakarta.inject.Inject;
import lombok.ToString;
import org.upe.controllers.interfaces.ArticleControllerInterface;
import org.upe.persistence.DAO.ArticleDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;




public class ArticleController implements ArticleControllerInterface {
    @Inject
    private ArticleDAO articleDAO;


    public void createArticle(UserInterface user, String name, String articleAbstract) {
        articleDAO.create(name, articleAbstract, user);
    }



    public boolean submitArticle(ArticleInterface article, EventInterface event) {
       for (ArticleInterface a : event.getArticles()) {
           if (a.getId().equals(article.getId())) {
               return false;
           }
       }
       article.getSubmittedIn().add(event);
       articleDAO.update(article);
       return true;
    }
}
