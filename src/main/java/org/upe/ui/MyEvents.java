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
                    isRunning = userExitEvent(user);
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
            System.out.print("Escolha sua opção: ");
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
                case "3":
//                    MySubEvents.subEventMenu(user);
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
            System.out.println("Digite: \n[1] - Editar nome do evento\n[2] - Editar data\n[3] - Editar local\n[4] - Editar " +
                    "Descrição\n[5] - Editar Organização do evento\n[6] - Apagar evento\n[7] - Voltar");
            System.out.println("Escolha sua opção");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = editEventName((myEvent.getId()));
                    break;
                case "2":
                    isRunning = editEventDate(myEvent.getId());
                    break;
                case "3":
                    isRunning = editEventLocal(myEvent.getId());
                    break;
                case "4":
                    isRunning = editEventDescription(myEvent.getId());
                    break;
                case "5":
                    isRunning = editEventOrganization(myEvent.getId());
                    break;
                case "6":
                    isRunning = deleteEvent(myEvent.getId(), user);
                    break;
                case "7":
                    return false;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
            return true;
        }
        return isRunning;
    }

    private static boolean editEventName(String id) {
        Scanner sc = new Scanner((System.in));
        System.out.print("Digite o novo nome do evento: ");
        String name = sc.nextLine();

        boolean newName = EventController.editEventName(id, name);
        System.out.println("Nome do evento alterado!");

        return newName;
    }

    private static boolean editEventDate(String id) {
        Scanner sc = new Scanner((System.in));
        System.out.print("Digite a nova data do evento: ");
        String date = sc.nextLine();

        boolean newDate = EventController.editEventDate(id, date);
        System.out.println("Data do evento alterada!");

        return newDate;
    }

    private static boolean editEventLocal(String id) {
        Scanner sc = new Scanner((System.in));
        System.out.print("Digite o novo local do evento: ");
        String local = sc.nextLine();

        boolean newLocal = EventController.editEventLocal(id, local);
        System.out.println("Local do evento alterado!");

        return newLocal;
    }


    private static boolean editEventDescription(String id) {
        Scanner sc = new Scanner((System.in));
        System.out.print("Digite o novo local do evento: ");
        String description = sc.nextLine();

        boolean newDescription = EventController.editEventDescription(id, description);
        System.out.println("Descrição do evento alterada!");

        return newDescription;
    }

    private static boolean editEventOrganization(String id) {
        Scanner sc = new Scanner((System.in));
        System.out.print("Digite o nome do novo organizador do evento: ");
        String organization = sc.nextLine();

        boolean newOrganization = EventController.editEventOrganization(id, organization);
        System.out.println("Organizador do evento alterado!");

        return newOrganization;
    }

    private static boolean deleteEvent(String id, UserInterface user) {
        boolean eventDel = EventController.deleteEvent(id, user);
        System.out.println("Evento deletado!");

        return eventDel;
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


    private static boolean userExitEvent(UserInterface user) {
        ArrayList<EventInterface> myEvents = UserController.userEventsIn(user.getCPF());

        int cont = 0;
        if (myEvents.isEmpty()) {
            System.out.println("Não há eventos para sair.");
            return true;
        }

        for (EventInterface event : myEvents) {
            System.out.println(event.toString(cont));
            cont++;
        }

        EventInterface event = Utils.chooseEventToLeave(myEvents);

        if(event == null) {
            return true;
        }

        if(EventController.deleteAttendeeOnList(user, event) ) {
            System.out.printf("Você saiu do evento %s\n", event.getName());
        }

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

        EventInterface event = Utils.chooseEventOnList(events);

        if(event == null) {
            return true;
        }

        if(EventController.addAttendeeOnList(user, event) ) {
            System.out.printf("Você se inscreveu no evento %s\n", event.getName());
        } else {
            System.out.println("Você já está inscrito nesse evento");
        }
        return true;
    }

}

