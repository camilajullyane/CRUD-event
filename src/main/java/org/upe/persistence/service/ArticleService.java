package org.upe.persistence.service;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.repository.ArticleUtility;

import java.util.List;

public class ArticleService {
    private final ArticleUtility articleUtility = new ArticleUtility();

    public void submitArticle(String cpf, String articleName, String eventID, String articleAbstract) {
        articleUtility.submitArticle(cpf, articleName, eventID, articleAbstract);
    }

    public List<Article> getAllArticles() {
        return articleUtility.getAllArticles();
    }

    public ArticleInterface createArticle(String name, String userCPF, String articleAbstract) {
        return articleUtility.createArticle(name, userCPF, articleAbstract);
    }

    public ArticleInterface getArticleById(String articleID) {
        return articleUtility.getArticleById(articleID);
    }

    public List<ArticleInterface> getAllArticlesByUser(String userCPF) {
        return articleUtility.getAllArticlesByUser(userCPF);
    }
}
