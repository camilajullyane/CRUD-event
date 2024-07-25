package org.upe.ui;

import org.upe.controllers.EventController;
import org.upe.controllers.UserController;
import org.upe.persistence.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MyEvents {
    public static boolean menuEvents(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            showMyEventsMenu();
            System.out.print("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = createEventMenu(user);
                    break;
                case "2":
                    isRunning = userEnterEvent(user);
                    break;
                case "3":
                    isRunning = exitEvent(user.getCPF());
                    break;
                case "4":
                    System.out.println("---Submeter artigo---");
                    break;
                case "5":
                    isRunning = myEvents(user);
                    break;
                case "6":
                    isRunning = showMyEvents(user.getCPF());
                    break;
                case "7":
                    return true;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
        }
        return false;
    }

    private static void showMyEventsMenu() {
        System.out.println("Digite: \n[1] - Criar evento" +
                "\n[2] - Participar de um evento" +
                "\n[3] - Sair de um evento" +
                "\n[4] - Submeter artigo" +
                "\n[5] - Ver eventos criados" +
                "\n[6] - Ver eventos que participo" +
                "\n[7] - Voltar ao menu");
    }


    private static boolean showAllEvents() {
        System.out.println("------------Participar de um evento--------------");
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

    private static boolean showMyEvents(String ownerCPF) {
        System.out.println("-------------Eventos que participo---------------");
        ArrayList<EventInterface> events = UserController.userEventsIn(ownerCPF);
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


    private static boolean myEvents(UserInterface user) {
        System.out.println("---------------Ver eventos criados---------------");

        ArrayList<EventInterface> myEvents = EventController.eventByUser(user.getCPF());
        int cont = 0;

        if (myEvents.isEmpty()) {
            System.out.println("Não há eventos para mostrar.");
            return true;
        }

        for (EventInterface event : myEvents) {
            System.out.println(event.toString(cont));
            cont++;
        }

        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            System.out.println("Digite:\n[1] - Editar um dos eventos\n[2] - Voltar ao menu");
            System.out.println("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    EventInterface event;
                    if((event = Utils.chooseEventOnList(myEvents)) == null) {
                        break;
                    }
                    isRunning = MyEvents.editMyEvent(user, event);
                    break;
                case "2":
                    return true;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
        }
        return true;
    }

    private static boolean editMyEvent(UserInterface user, EventInterface myEvent) {
        System.out.printf("Editando evento %s\n", myEvent.getName());
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Digite: \n[1] - Editar nome\n[2] - Editar data\n[3] - editar local\n[4] - Editar Descrição\n[5] - Apagar evento\n[6] - voltar");
            System.out.println("Escolha sua opção");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    return false;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
            return true;
        }
        return isRunning;
    }

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

    private static boolean exitEvent(String ownerCPF) {
        Scanner sc = new Scanner(System.in);
        showMyEvents(ownerCPF);
        System.out.println("----------Sair de um evento------------");

        System.out.print("Digite o número do evento que você quer sair: ");
        String name = sc.nextLine();

//        boolean event = EventController.deleteAttendeeOnList();
        return true;
    }

    private static boolean userEnterEvent(UserInterface user){
        ArrayList<EventInterface> events = EventController.showAllEvents();
        System.out.println("---------------Ver eventos criados---------------");
           int cont = 0;
           if (events.isEmpty()) {
                System.out.println("Não há eventos para se inscrever.");
                return true;
           }

           for (EventInterface event : events) {
               System.out.println(event.toString(cont));
                cont++;
           }

        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Qual evento você quer entrar? (-1 para voltar) ");
            String input = sc.nextLine();
            if (input.equals("-1")) {
                isRunning = false;
            } else if (input.matches("\\d+") || input.equals("0")) {
                int eventNumber = Integer.parseInt(input);
                if (eventNumber < events.size()) {
                    if(EventController.addAttendeeOnList(user, events.get(eventNumber))) {
                        System.out.printf("Você se inscreveu no evento %s\n", events.get(eventNumber).getName());
                        isRunning = false;
                    } else {
                        System.out.println("Você já está inscrito nesse evento");
                    }

                } else {
                    System.out.print("[ERRO] Número do evento inválido.");
                }
            } else {
                System.out.println("[ERRO] Entrada inválida. Digite apenas números.");
            }
        }
           return true;
    }
}

