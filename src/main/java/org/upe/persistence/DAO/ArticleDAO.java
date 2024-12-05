package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();

    public ArticleInterface create(String title, String articleAbstract, UserInterface user) {
        Article article = new Article(title, articleAbstract, user);
        entityManager.getTransaction().begin();
        entityManager.persist(article);
        entityManager.getTransaction().commit();
        return article;
    }

    public void delete(String id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Article.class, id));
        entityManager.getTransaction().commit();
    }

    public ArticleInterface findById(String id) {
        return entityManager.find(Article.class, id);
    }

    public ArticleInterface update(ArticleInterface article) {
        entityManager.getTransaction().begin();
        entityManager.merge(article);
        entityManager.getTransaction().commit();
        return article;
    }

    public List<ArticleInterface> getAll() {
        List<Article> articles = entityManager.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        return new ArrayList<>(articles);
    }
}
