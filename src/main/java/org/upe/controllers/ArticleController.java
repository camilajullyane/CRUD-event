package org.upe.controllers;

import org.upe.controllers.interfaces.ArticleControllerInterface;
import org.upe.persistence.DAO.ArticleDAO;
import org.upe.persistence.DAO.EventDAO;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

public class ArticleController implements ArticleControllerInterface {
    private final ArticleDAO articleDAO;
    private final EventDAO eventDAO;

    public ArticleController(ArticleDAO articleDAO, EventDAO eventDAO) {
        this.articleDAO = articleDAO;
        this.eventDAO = eventDAO;
    }

    public ArticleInterface createArticle(UserInterface user, String name, String articleAbstract) {
        ArticleInterface article = articleDAO.create(name, articleAbstract, user);
        user.addArticle(article);
        return article;
    }

    public boolean submitArticle(ArticleInterface article, EventInterface event) {
        for (ArticleInterface a : event.getArticles()) {
            if (a.getId().equals(article.getId())) {
                return false;
            }
        }

        article.addSubmittedIn(event);
        articleDAO.update(article);
        event.addArticleOnEvent(article);
        eventDAO.update(event);
        return true;
    }

    public boolean deleteArticle(ArticleInterface article) {
        articleDAO.delete(article.getId());
        return true;
    }
}
