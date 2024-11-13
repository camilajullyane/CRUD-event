package org.upe.controllers;

import org.upe.controllers.interfaces.ArticleControllerInterface;
import org.upe.persistence.DAO.ArticleDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;




public class ArticleController implements ArticleControllerInterface {

    private static final ArticleDAO articleDAO = new ArticleDAO();


    public void createArticle(UserInterface user, String name, String articleAbstract) {
        articleDAO.create(name, articleAbstract, user);
    }



    public boolean submitArticle(ArticleInterface article, EventInterface event) {
       for (String articleID : event.getArticleList()) {
           if (articleID.equals(article.getId())) {
               return false;
           }
       }
       article.getSubmittedIn().add(event);
       articleDAO.update(article);
       return true;
    }
}
