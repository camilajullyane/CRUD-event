package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger LOGGER = Logger.getLogger(ArticleDAO.class.getName());

    public ArticleInterface create(String title, String articleAbstract, UserInterface user) {
        try {
            Article article = Article.Builder()
                    .withTitle(title)
                    .withArticleAbstract(articleAbstract)
                    .withUser((User) user)
                    .build();
            
            entityManager.getTransaction().begin();
            entityManager.persist(article);
            entityManager.getTransaction().commit();
    
            return article;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        Article article = entityManager.find(Article.class, id);
        entityManager.remove(article);
        entityManager.getTransaction().commit();
    }


    public ArticleInterface findById(UUID id) {
        try {
            return entityManager.find(Article.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public ArticleInterface update(ArticleInterface article) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(article);
            entityManager.getTransaction().commit();
            return article;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public List<ArticleInterface> getAll() {
        try {
            List<Article> articles = entityManager.createQuery("SELECT a FROM Article a", Article.class).getResultList();
            return new ArrayList<>(articles);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
