package org.upe.ui;

import org.upe.controllers.EventController;
import org.upe.persistence.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MyEvents {
    public static boolean menuEvents(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            showMyEventsMenu();
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = createEventMenu(user);
                    break;
                case "2":
                    isRunning = showAllEvents();
                    break;
                case "3":
                    //isRunning = exitEvent();
                    break;
                case "4":
                    System.out.println("---Submeter artigo---");
                    break;
                case "5":
                    isRunning = myEvents(user.getCPF());
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


    private static void eventsConfig(String eventID, String CPF) {
        Scanner sc = new Scanner(System.in);

        System.out.println("[1] - Atualizar nome do evento\n" +
                "[2] - Atualizar descrição\n" +
                "[3] - Atualizar organização\n" +
                "[4] - Atualizar local\n + " +
                "[5] - Atualizar data\n");

        int option = sc.nextInt();
        switch (option) {
            case 1:


                break;
        }
    }


    private static boolean showAllEvents() {
        System.out.println("---Participar de um evento---");
        ArrayList<EventInterface> events = EventController.showAllEvents();
        int cont = 0;

        if (events.isEmpty()) {
            System.out.println("Não há eventos para mostrar.");
        }

        for (EventInterface event : events) {
            cont++;
            System.out.println(event.toString(cont));
        }
        return true;
    }

    private static boolean myEvents(String ownerCPF) {
        System.out.println("---Ver meus eventos---");

        ArrayList<EventInterface> myEvents = EventController.eventByUser(ownerCPF);
//        int cont = 0;

        if (myEvents.isEmpty()) {
            System.out.println("Não há eventos para mostrar.");
        }

        for (EventInterface event : myEvents) {
            System.out.println(event.toString());
        }
        return true;
    }

//    private static boolean addAttendeeOnEvent(String CPF, String eventID) {
//
//    }

    private static boolean createEventMenu(UserInterface user) {
        Scanner sc = new Scanner(System.in);

        System.out.println("----Criar evento----");
        System.out.print("Nome do evento: ");
        String name = sc.nextLine();
        System.out.print("Descrição do evento: ");
        String description = sc.nextLine();
        String date = Utils.validateDate();
        System.out.print("Nome do local: ");
        String local = sc.nextLine();
        System.out.print("Organização: ");
        String organization = sc.nextLine();
        EventInterface event = EventController.createEvent(user, name, description, date, local, organization);
        System.out.printf("Evento %s criado\n", event.getName());
        return true;
    }

    private static boolean exitEvent() {
        Scanner sc = new Scanner(System.in);

        System.out.println("---Sair de um evento---");

        System.out.print("Digite o número do evento que você quer sair: ");
        String name = sc.nextLine();

//        boolean event = EventController.deleteEvent();
        return true;
    }
}
