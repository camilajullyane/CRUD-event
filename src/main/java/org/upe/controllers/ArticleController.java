package org.upe.controllers;

import org.upe.controllers.interfaces.ArticleControllerInterface;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.service.ArticleService;
import org.upe.persistence.service.UserService;

import java.util.List;

public class ArticleController implements ArticleControllerInterface {
    private static final UserService userService = new UserService();
    private static final EventUtility eventUtility = new EventUtility();
    private static final ArticleService articleService = new ArticleService();

    public void createArticle(UserInterface user, String name, String articleAbstract) {
        ArticleInterface article = articleService.createArticle(name, user.getCPF(), articleAbstract);
        userService.addUserArticle(user.getCPF(), article.getArticleID());
    }

    public List<ArticleInterface> getAllArticlesByUser(String userCPF) {
        return articleService.getAllArticlesByUser(userCPF);
    }


    public boolean submitArticle(ArticleInterface article, EventInterface event) {
       for (String articleID : event.getArticleList()) {
           if (articleID.equals(article.getArticleID())) {
               return false;
           }
       }
        return eventUtility.addArticleOnList(article.getArticleID(), event.getId());
    }


}
