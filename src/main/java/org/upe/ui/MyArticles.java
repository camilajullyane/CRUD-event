package org.upe.ui;

import org.upe.controllers.ArticleController;
import org.upe.controllers.EventController;
import org.upe.persistence.ArticleInterface;
import org.upe.persistence.EventInterface;
import org.upe.persistence.UserInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class MyArticles {
    public static boolean myArticlesMenu(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            showMyArticleMenu();
            System.out.print("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = createArticleMenu(user);
                    break;
                case "2":
                    isRunning = submitArticle(user);
                    break;
                case "3":
                    return true;
            }
        }
        return true;
    }

    public static void showMyArticleMenu() {
        System.out.println("Digite:\n" +
                "[1] - Criar novo artigo\n" +
                "[2] - Submeter artigo a um evento\n" +
                "[3] - Voltar menu");
    }

    public static boolean createArticleMenu(UserInterface user) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Título do artigo:");
        String title = sc.nextLine();

        System.out.print("Resumo do artigo:");
        String articleAbstract = sc.nextLine();

        ArticleInterface article = ArticleController.createArticle(user, title, articleAbstract);
        System.out.printf("Artigo %s foi criado.", article.getName());
        return true;
    }


    private static ArrayList<ArticleInterface> showMyArticles(String ownerCPF) {
        System.out.println("-------------Meus artigos---------------");
        ArrayList<ArticleInterface> articles = ArticleController.getAllArticlesByUser(ownerCPF);

        if (articles.isEmpty()) {
            System.out.println("Não há artigos para mostrar.");
        }

        int cont = 0;
        for (ArticleInterface article : articles) {
            System.out.println(article.toString(cont));
            cont++;
        }
        return articles;
    }

    private static boolean submitArticle(UserInterface user) {
        ArrayList<ArticleInterface> articles = showMyArticles(user.getCPF());
        ArrayList<EventInterface> events = EventController.getAllEvents();
        ArticleInterface chosenArticle = Utils.chooseArticleOnList(articles);

        if(!MyEvents.showAllEvents()) {
            System.out.println("Não há eventos para submter artigos.");
            return true;
        }

        System.out.println("Em qual evento você deseja submeter esse artigo?");
        EventInterface event = Utils.chooseEventOnList(events);
        if (event == null) {
            return true;
        }

        boolean validate = ArticleController.submitArticle(chosenArticle, event);
        if (validate) {
            System.out.println("Artigo submetido com sucesso!");
        } else {
            System.out.println("Você já submeteu esse artigo nesse evento.");
        }
        return true;
    }
}


