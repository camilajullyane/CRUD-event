package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArticleDAOTest {
    private EntityManager mockEntityManager;
    private EntityTransaction mockTransaction;
    private ArticleDAO articleDAO;

    @BeforeEach
    void setUp() {
        mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        articleDAO = new ArticleDAO(mockEntityManager);
    }

    @Test
    void testCreateArticle() {
        String title = "Article Title";
        String articleAbstract = "Article Abstract";
        UserInterface user = new User();

        ArticleInterface article = articleDAO.create(title, articleAbstract, user);

        assertNotNull(article);
        verify(mockEntityManager).persist(article);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCreateArticleWithException() {
        String title = "Article Title";
        String articleAbstract = "Article Abstract";
        UserInterface user = new User();


        doThrow(new RuntimeException("Exception during persist")).when(mockEntityManager).persist(any(Article.class));

        ArticleInterface article = articleDAO.create(title, articleAbstract, user);

        assertNull(article);
        verify(mockEntityManager).persist(any(Article.class));
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testDeleteArticle() {
        UUID id = UUID.randomUUID();
        Article article = new Article();
        when(mockEntityManager.find(Article.class, id)).thenReturn(article);

        articleDAO.delete(id);

        verify(mockEntityManager).remove(article);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Article article = new Article();
        article.setId(id);
        when(mockEntityManager.find(Article.class, id)).thenReturn(article);

        ArticleInterface foundArticle = articleDAO.findById(id);

        assertNotNull(foundArticle);
        assertEquals(id, foundArticle.getId());
        verify(mockEntityManager).find(Article.class, id);
    }

    @Test
    void testFindByIdWithException() {
        UUID id = UUID.randomUUID();
        when(mockEntityManager.find(Article.class, id)).thenThrow(new RuntimeException("Exception during find"));

        ArticleInterface foundArticle = articleDAO.findById(id);

        assertNull(foundArticle);
        verify(mockEntityManager).find(Article.class, id);
    }

    @Test
    void testUpdateArticle() {
        UUID id = UUID.randomUUID();
        Article article = new Article();
        article.setId(id);
        when(mockEntityManager.merge(article)).thenReturn(article);

        ArticleInterface updatedArticle = articleDAO.update(article);

        assertNotNull(updatedArticle);
        assertEquals(id, updatedArticle.getId());
        verify(mockEntityManager).merge(article);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateArticleWithException() {
        UUID id = UUID.randomUUID();
        Article article = new Article();
        article.setId(id);

        doThrow(new RuntimeException("Exception during merge")).when(mockEntityManager).merge(any(Article.class));

        ArticleInterface updatedArticle = articleDAO.update(article);

        assertNull(updatedArticle);

        verify(mockEntityManager).merge(article);
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testGetAll() {
        TypedQuery<Article> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery("SELECT a FROM Article a", Article.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(null);

        articleDAO.getAll();

        verify(mockEntityManager).createQuery("SELECT a FROM Article a", Article.class);
        verify(mockQuery).getResultList();
    }

    @Test
    void testGetAllWithException() {
        TypedQuery<Article> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery("SELECT a FROM Article a", Article.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenThrow(new RuntimeException("Exception during get all"));

        articleDAO.getAll();

        verify(mockEntityManager).createQuery("SELECT a FROM Article a", Article.class);
        verify(mockQuery).getResultList();
    }
}
