package org.upe.ui;

import org.upe.controllers.EventController;
import org.upe.persistence.EventInterface;
import org.upe.persistence.UserInterface;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalTime;
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
                    System.out.println("Criar evento =)");
                    isRunning = createEventMenu(user);
                    break;
                case "2":
                    System.out.println("Participar de um evento");
                    isRunning = showAllEvents();
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


    private static boolean showAllEvents() {
        Scanner sc = new Scanner(System.in);
        ArrayList<EventInterface> events = EventController.showAllEvents();

        if (events.isEmpty()) {
            System.out.println("Não há eventos para mostrar.");
            return false;
        }

        for (EventInterface event : events) {
            System.out.println(event);
        }
        return true;
    }


    private static boolean createEventMenu(UserInterface user) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Criar evento");
        System.out.print("Nome do evento: ");
        String name = sc.nextLine();
        System.out.print("Descrição do evento: ");
        String description = sc.nextLine();
        Date date = Utils.validateDate();
        System.out.print("Nome do local: ");
        String local = sc.nextLine();
        System.out.print("Organização: ");
        String organization = sc.nextLine();
        EventController.createEvent(user, name, description, date, local, organization);
        return true;
    }

    private static boolean exitEvent() {
        return true;
    }
}
