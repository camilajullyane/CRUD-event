package org.upe.ui;

import org.upe.persistence.UserInterface;

import java.util.Scanner;


public class MainMenu {
    protected static boolean menu(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Usuário LOGADO");
        System.out.println("Menu principal:");
        boolean isRunning = true;
        while (isRunning) {
            System.out.printf("Usuário: %s\n", user.getName());
            System.out.println("[1] - Meus eventos\n[2] - Meus artigos\n[3] - Configurações da conta\n[4] - Sair da conta\n[5] - Fechar aplicativo");
            System.out.print("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
//                    System.out.println("ir pros meus eventos");
                    isRunning = MyEvents.menuEvents(user);
                    break;
                case "2":
                    isRunning = MyArticles.myArticlesMenu(user);
                    break;
                case "3":
                    System.out.println("saindo...");
                    break;
                case "4":
                    System.out.println("saindo...");
                    return true;
                case "5":
                    return false;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
                    break;
            }
        }
        return false;
    }
}
