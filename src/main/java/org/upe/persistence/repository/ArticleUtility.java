package org.upe.persistence.repository;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import org.upe.persistence.model.Event;
import org.upe.persistence.model.User;

import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleUtility {
    private  final UserUtility userUtility = new UserUtility();
    private  final EventUtility eventUtility = new EventUtility();

    private static final Logger LOGGER = Logger.getLogger(ArticleUtility.class.getName());
    protected static String csvFilePath = "DB/articles.csv";

    public void setCsvFilePath(String csvFilePath) {
        ArticleUtility.csvFilePath = csvFilePath;
    }

    public void submitArticle(String CPF, String articleName, String eventID, String articleAbstract) {
        List<User> users = userUtility.getAllUsers();
        List<Event> events = eventUtility.getAllEvents();

        for (Event event : events) {
            if (event.getId().equals(eventID)) {
                for (User user : users) {
                    if (user.getCPF().equals(CPF)) {
                        Article newArticle = new Article(articleName, eventID, CPF, articleAbstract);
                        user.addArticleID(newArticle.getArticleID());
                        event.addArticleList(newArticle.getArticleID());
                    }
                }
            }
        }
        eventUtility.saveEvents(events);
        userUtility.updateFileData(users);
    }

    public  List<Article> getAllArticles() {
        List<Article> articlesArray = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] newArticleLine = line.split(",", -1);
                Article article = new Article(
                        newArticleLine[0],
                        newArticleLine[1],
                        newArticleLine[2],
                        newArticleLine[3]);
                articlesArray.add(article);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo CSV", e);
        }

        return articlesArray;
    }

    public String generateArticleID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        while (getArticleById(uuidString)!= null) {
            uuidString = UUID.randomUUID().toString();
        }
        return uuidString;
    }

    public ArticleInterface createArticle(String name, String userCPF, String articleAbstract) {
        List<Article> articles = getAllArticles();
        String articleID = generateArticleID();

        Article newArticle = new Article(name, articleID, userCPF, articleAbstract);
        articles.add(newArticle);

        updateArticleFileData(articles);

        return newArticle;
    }

    public  Article getArticleById(String articleID) {
        List<Article> articles = getAllArticles();

        for(Article article : articles) {
            if(article.getArticleID().equals(articleID)) {
                return article;
            }
        }
        return null;
    }

    public void updateArticleFileData(List<Article> newData) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(csvFilePath))) {
            write.write("name,articleID,userCPF,articleAbstract\n");
            for (Article article : newData) {
                String line = String.format("%s,%s,%s,%s%n", article.getName(), article.getArticleID(), article.getUserCPF(), article.getArticleAbstract());
                write.write(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo CSV", e);
        }
    }

    public List<ArticleInterface> getAllArticlesByUser(String userCPF) {
        List<Article> allArticles = getAllArticles();
        ArrayList<ArticleInterface> userArticles = new ArrayList<>();

        for (Article article : allArticles) {
            if (article.getUserCPF().equals(userCPF)) {
                userArticles.add(article);
            }
        }
        if (userArticles.isEmpty()) {
            return new ArrayList<>();
        }
        return userArticles;
    }
}
