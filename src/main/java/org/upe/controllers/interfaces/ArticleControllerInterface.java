package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

public interface ArticleControllerInterface {
    ArticleInterface createArticle(UserInterface user, String name, String articleAbstract);
    boolean submitArticle(ArticleInterface article, EventInterface event);
    boolean deleteArticle(ArticleInterface article);
}