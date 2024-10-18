package org.upe.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleUtilityTest {
    private ArticleUtility articleUtility;
    private UserUtility userUtility;
    private EventUtility eventUtility;

    @BeforeEach
    void setUp() {
        articleUtility = new ArticleUtility();
        userUtility = new UserUtility();
        eventUtility = new EventUtility();

        articleUtility.setCsvFilePath("DB/teste/test_articles.csv"); // Define arquivo de teste

        try (FileWriter writer = new FileWriter("DB/teste/test_articles.csv")) {
            writer.write("name,articleID,userCPF,articleAbstract\n");
            writer.write("Test Article,123e4567-e89b-12d3-a456-426614174000,123456789,Test Abstract\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter writer = new FileWriter("DB/teste/test_users.csv")) {
            writer.write("name,email,cpf,password,attendeeOn,ownerOf,articleID\n");
            writer.write("John Doe,john.doe@example.com,123456789,password,,,\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter writer = new FileWriter("DB/teste/test_events.csv")) {
            writer.write("name,id,location,startTime,endTime,articleList\n");
            writer.write("Test Event,1,Test Location,2024-10-01T10:00,2024-10-01T12:00,\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userUtility.setCsvFilePath("DB/teste/test_users.csv");
        eventUtility.setCsvFilePath("DB/teste/test_events.csv");
    }

    @Test
    void testSubmitArticle() {
        articleUtility.submitArticle("123456789", "New Article", "1", "New Abstract");

        List<User> users = userUtility.getAllUsers();
        User user = users.stream().filter(u -> u.getCPF().equals("123456789")).findFirst().orElse(null);
        assertNotNull(user, "The user should exist");

        List<Event> events = eventUtility.getAllEvents();
        Event event = events.stream().filter(e -> e.getId().equals("1")).findFirst().orElse(null);
        assertNotNull(event, "The event should exist");
    }

    @Test
    void testGetAllArticles() {
        List<Article> articles = articleUtility.getAllArticles();

        assertNotNull(articles, "Article list should not be null");
        assertFalse(articles.isEmpty(), "Article list should not be empty");
        assertEquals("Test Article", articles.get(0).getName(), "First article name should match");
    }

    @Test
    void testGenerateArticleID() {
        String articleID = articleUtility.generateArticleID();
        assertNotNull(articleID, "Generated ID should not be null");
        assertFalse(articleID.isEmpty(), "Generated ID should not be empty");
    }

    @Test
    void testCreateArticle() {
        Article newArticle = (Article) articleUtility.createArticle("New Article", "123456789", "New Abstract");

        assertNotNull(newArticle, "Created article should not be null");
        assertEquals("New Article", newArticle.getName(), "Article name should match");
        assertEquals("123456789", newArticle.getUserCPF(), "User CPF should match");

        List<Article> articles = articleUtility.getAllArticles();
        assertTrue(articles.contains(newArticle), "The new article should be saved in the list");
    }

    @Test
    void testGetArticleById() {
        Article article = articleUtility.getArticleById("123e4567-e89b-12d3-a456-426614174000");
        assertNotNull(article, "Article should not be null");
        assertEquals("Test Article", article.getName(), "Article name should match");
    }

    @Test
    void testGetAllArticlesByUser() {
        List<ArticleInterface> articles = articleUtility.getAllArticlesByUser("123456789");

        assertNotNull(articles, "Article list should not be null");
        assertFalse(articles.isEmpty(), "Article list should not be empty");
        assertEquals("Test Article", articles.get(0).getName(), "Article name should match");
    }

    @Test
    void testUpdateArticleFileData() {
        List<Article> articles = articleUtility.getAllArticles();
        articles.get(0).setName("Updated Article");

        articleUtility.updateArticleFileData(articles);

        List<Article> updatedArticles = articleUtility.getAllArticles();
        assertEquals("Updated Article", updatedArticles.get(0).getName(), "Article name should be updated");
    }

    @Test
    void testCreateArticleWithExistingID() {
        // Test case where article with the same ID already exists
        Article newArticle = (Article) articleUtility.createArticle("Duplicate Article", "123456789", "Duplicate Abstract");
        assertNotNull(newArticle, "The new article should be created with a unique ID");
        assertNotEquals("123e4567-e89b-12d3-a456-426614174000", newArticle.getArticleID(), "The article ID should be unique");
    }
}
