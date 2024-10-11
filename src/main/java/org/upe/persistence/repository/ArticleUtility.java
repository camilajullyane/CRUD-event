package org.upe.persistence.repository;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.model.Article;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class ArticleUtility {
    protected static String csvFilePath = "DB/articles.csv";
  
    private ArticleUtility() {
        throw new UnsupportedOperationException("Essa é uma utilityClass e não pode ser instânciada");
    }

    public static void setCsvFilePath(String csvFilePath) {
        ArticleUtility.csvFilePath = csvFilePath;
    }



//    public static void submitArticle(String CPF, String articleName, String eventID) {
//        ArrayList<User> users = UserUtility.getAllUsers();
//        ArrayList<Event> events = EventUtility.getAllEvents();
//
//        for (Event event : events) {
//            if (event.getId().equals(eventID)) {
//                for (User user : users) {
//                    if (user.getCPF().equals(CPF)) {
//                        // Criar um novo artigo
//                        Article newArticle = new Article(articleName, eventID, CPF);
//
//                        // Adicionar o artigo à lista de artigos do usuário
//                        User.getArticleID().add(newArticle);
//
//                        // Adicionar o artigo à lista de artigos submetidos do evento
//                        event.getSubmittedArticles().add(newArticle);
//
//                        // Atualizar o arquivo de usuários e eventos, se necessário
//                        UserUtility.updateUser(user);
//                        EventUtility.updateEvent(event);
//                        return;
//                    }
//                }
//            }
//        }
//    }

    public static List<Article> getAllArticles() {
        List<Article> articlesArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
            String headerLine = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newArticleLine = line.split(",", -1);
                Article article = new Article(
                        newArticleLine[0],
                        newArticleLine[1],
                        newArticleLine[2],
                        newArticleLine[3]);
                articlesArray.add(article);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articlesArray;
    }


    public static String generateArticleID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        while (getArticleById(uuidString)!= null) {
            uuidString = UUID.randomUUID().toString();
        }
        return uuidString;
    }

    public static ArticleInterface createArticle(String name, String userCPF, String articleAbstract) {
        List<Article> articles = ArticleUtility.getAllArticles();
        String articleID = generateArticleID();

        Article newArticle = new Article(name, articleID, userCPF, articleAbstract);
        articles.add(newArticle);

        updateArticleFileData(articles);

        return newArticle;
    }

    public static Article getArticleById(String articleID) {
        List<Article> articles = getAllArticles();

        for(Article article : articles) {
            if(article.getArticleID().equals(articleID)) {
                return article;
            }
        }
        return null;
    }

    private static void updateArticleFileData(List<Article> newData) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(csvFilePath));
            write.write("name,articleID,userCPF,articleAbstract\n");
            for (Article article : newData) {
                String line = String.format("%s,%s,%s,%s%n", article.getName(), article.getArticleID(), article.getUserCPF(), article.getArticleAbstract());
                write.write(line);
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  ArrayList<ArticleInterface> getAllArticlesByUser(String userCPF) {
        List<Article> allArticles = ArticleUtility.getAllArticles();
        ArrayList<ArticleInterface> userArticles = new ArrayList<>();

        for (Article article : allArticles) {
            if (article.getUserCPF().equals(userCPF)) {
                userArticles.add(article);
            }
        }
        if (userArticles.isEmpty()) {
            return null;
        }
        return userArticles;
    }
}
