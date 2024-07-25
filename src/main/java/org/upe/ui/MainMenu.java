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
            System.out.println("[1] - Meus eventos\n[2] - Configurações da conta\n[3] - Sair da conta\n[4] - Fechar aplicativo");
            System.out.print("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
//                    System.out.println("ir pros meus eventos");
                    isRunning = MyEvents.menuEvents(user);
                    break;
                case "2":
                    System.out.println("saindo...");
                    break;
                case "3":
                    System.out.println("saindo...");
                    return true;
                case "4":
                    return false;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
                    break;
            }
        }
        return false;
    }
}
