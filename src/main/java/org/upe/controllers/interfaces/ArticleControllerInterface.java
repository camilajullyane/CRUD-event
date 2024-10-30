package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.List;

public interface ArticleControllerInterface {
    void createArticle(UserInterface user, String name, String articleAbstract);
    List<ArticleInterface> getAllArticlesByUser(String userCPF);
    boolean submitArticle(ArticleInterface article, EventInterface event);
}