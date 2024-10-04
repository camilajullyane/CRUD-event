package org.upe.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.repository.ArticleUtility;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArticleUtilityTest {

    private static final String TEST_CSV_FILE_PATH = "DB/test_articles.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Criação de um arquivo CSV temporário para fins de teste
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CSV_FILE_PATH))) {
            writer.write("name,articleID,userCPF,articleAbstract\n");
            writer.write("Article 1,123e4567-e89b-12d3-a456-556642440000,123456789,Abstract 1\n");
            writer.write("Article 2,123e4567-e89b-12d3-a456-556642440001,987654321,Abstract 2\n");
            writer.write("Article 3,123e4567-e89b-12d3-a456-556642440002,123456789,Abstract 3\n");
        }
        // Atualizar o caminho do CSV para os testes
        ArticleUtility.CSV_FILE_PATH = TEST_CSV_FILE_PATH;
    }

    @Test
    void getAllArticles() {
        ArrayList<Article> actualArticles = ArticleUtility.getAllArticles();

        assertEquals(3, actualArticles.size(), "O número de artigos deve ser o mesmo");

        ArticleInterface article1 = actualArticles.get(0);
        assertEquals("Article 1", article1.getName(), "O nome do primeiro artigo deve ser 'Article 1'");
        assertEquals("123e4567-e89b-12d3-a456-556642440000", article1.getArticleID(), "O ID do primeiro artigo deve ser '123e4567-e89b-12d3-a456-556642440000'");
        assertEquals("123456789", article1.getUserCPF(), "O CPF do usuário do primeiro artigo deve ser '123456789'");
        assertEquals("Abstract 1", article1.getArticleAbstract(), "O resumo do primeiro artigo deve ser 'Abstract 1'");

        ArticleInterface article2 = actualArticles.get(1);
        assertEquals("Article 2", article2.getName(), "O nome do segundo artigo deve ser 'Article 2'");
        assertEquals("123e4567-e89b-12d3-a456-556642440001", article2.getArticleID(), "O ID do segundo artigo deve ser '123e4567-e89b-12d3-a456-556642440001'");
        assertEquals("987654321", article2.getUserCPF(), "O CPF do usuário do segundo artigo deve ser '987654321'");
        assertEquals("Abstract 2", article2.getArticleAbstract(), "O resumo do segundo artigo deve ser 'Abstract 2'");

        ArticleInterface article3 = actualArticles.get(2);
        assertEquals("Article 3", article3.getName(), "O nome do terceiro artigo deve ser 'Article 3'");
        assertEquals("123e4567-e89b-12d3-a456-556642440002", article3.getArticleID(), "O ID do terceiro artigo deve ser '123e4567-e89b-12d3-a456-556642440002'");
        assertEquals("123456789", article3.getUserCPF(), "O CPF do usuário do terceiro artigo deve ser '123456789'");
        assertEquals("Abstract 3", article3.getArticleAbstract(), "O resumo do terceiro artigo deve ser 'Abstract 3'");
    }

    @Test
    void generateArticleID() {
        String articleID = ArticleUtility.generateArticleID();
        assertNotNull(articleID, "O ID do artigo gerado não deve ser nulo");
        assertTrue(articleID.matches("^[0-9a-fA-F-]{36}$"), "O ID do artigo deve estar no formato UUID");
    }

    @Test
    void createArticle() {
        String name = "Article 4";
        String userCPF = "11223344556";
        String articleAbstract = "Abstract 4";

        ArticleInterface newArticle = ArticleUtility.createArticle(name, userCPF, articleAbstract);
        assertNotNull(newArticle, "O novo artigo não deve ser nulo");
        assertEquals(name, newArticle.getName(), "O nome do novo artigo deve ser 'Article 4'");
        assertEquals(userCPF, newArticle.getUserCPF(), "O CPF do usuário do novo artigo deve ser '11223344556'");
        assertEquals(articleAbstract, newArticle.getArticleAbstract(), "O resumo do novo artigo deve ser 'Abstract 4'");

        // Verifica se o novo artigo foi adicionado ao arquivo CSV
        ArrayList<Article> actualArticles = ArticleUtility.getAllArticles();
        assertEquals(4, actualArticles.size(), "O número de artigos deve ser 4 após a adição do novo artigo");
    }

    @Test
    void getArticleById() {
        ArticleInterface article = ArticleUtility.getArticleById("123e4567-e89b-12d3-a456-556642440000");
        assertNotNull(article, "O artigo com o ID '123e4567-e89b-12d3-a456-556642440000' deve existir");
        assertEquals("Article 1", article.getName(), "O nome do artigo deve ser 'Article 1'");

        Article nonExistentArticle = ArticleUtility.getArticleById("non-existent-id");
        assertNull(nonExistentArticle, "O artigo com o ID 'non-existent-id' não deve existir");
    }

    @Test
    void getAllArticlesByUser() {
        String userCPF = "123456789";
        ArrayList<ArticleInterface> userArticles = ArticleUtility.getAllArticlesByUser(userCPF);

        assertNotNull(userArticles, "A lista de artigos do usuário não deve ser nula");
        assertEquals(2, userArticles.size(), "O número de artigos do usuário deve ser 2");

        ArticleInterface article1 = userArticles.get(0);
        assertEquals("Article 1", article1.getName(), "O nome do primeiro artigo deve ser 'Article 1'");
        assertEquals("123e4567-e89b-12d3-a456-556642440000", article1.getArticleID(), "O ID do primeiro artigo deve ser '123e4567-e89b-12d3-a456-556642440000'");
        assertEquals("123456789", article1.getUserCPF(), "O CPF do usuário do primeiro artigo deve ser '123456789'");
        assertEquals("Abstract 1", article1.getArticleAbstract(), "O resumo do primeiro artigo deve ser 'Abstract 1'");

        ArticleInterface article2 = userArticles.get(1);
        assertEquals("Article 3", article2.getName(), "O nome do segundo artigo deve ser 'Article 3'");
        assertEquals("123e4567-e89b-12d3-a456-556642440002", article2.getArticleID(), "O ID do segundo artigo deve ser '123e4567-e89b-12d3-a456-556642440002'");
        assertEquals("123456789", article2.getUserCPF(), "O CPF do usuário do segundo artigo deve ser '123456789'");
        assertEquals("Abstract 3", article2.getArticleAbstract(), "O resumo do segundo artigo deve ser 'Abstract 3'");

        String nonExistentUserCPF = "non-existent-cpf";
        ArrayList<ArticleInterface> nonExistentUserArticles = ArticleUtility.getAllArticlesByUser(nonExistentUserCPF);
        assertNull(nonExistentUserArticles, "A lista de artigos do usuário inexistente deve ser nula");
    }
}
