package org.upe.ui;

import org.upe.persistence.UserInterface;

import java.util.Scanner;

public class MyEvents {

    public static boolean menuEvents(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        showMyEventsMenu();

        while (isRunning) {
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = false;
                    System.out.println("Criar evento =)");
                    break;
                case "2":
                    System.out.println("Participar de um evento");
                    break;
                case "3":
                    System.out.println("Sair de um evento");
                    break;
                case "4":
                    System.out.println("Submeter artigo");
                    break;
                case "5":
                    System.out.println("Ver meus eventos");
                    break;
                case "6":
                    return true;
            }
        }
        return false;
    }

    private static void showMyEventsMenu() {
        System.out.println("Digite: \n[1] - Criar evento" +
                "\n[2] - Participar de um evento" +
                "\n[3] - Sair de um evento" +
                "\n[4] - Submeter artigo" +
                "\n[5] - Ver meus eventos" +
                "\n[6] - Voltar ao menu");
    }

}
