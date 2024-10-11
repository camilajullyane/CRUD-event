package org.upe.ui2;

import org.upe.controllers.EventController;
import org.upe.controllers.SubEventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MySubEvents {
    public static boolean subEventMenu(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            showSubEventMenu();
            System.out.print("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = createSubEventMenu(user);
                    break;
                case "2":

                    isRunning = editSubEventMenu(user);
                    break;
                case "3":
                    isRunning = deleteSubEventMenu(user);
                    break;
                case "4":
                    return true;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
        }
        return false;
    }

    private static void showSubEventMenu() {
        System.out.println("Digite: \n[1] - Criar sub-evento" +
                "\n[2] - Editar sub-evento" +
                "\n[3] - Remover sub-evento" +
                "\n[4] - Voltar ao menu principal");
    }

    private static boolean createSubEventMenu(UserInterface user) {
        Scanner sc = new Scanner(System.in);

        List<EventInterface> events = EventController.getAllEventsByUser(user.getCPF());

        if (events.isEmpty()) {
            System.out.println("Você não tem nenhum evento.");
            return true;
        }

        int count = 0;
        for (EventInterface myEvents : events) {
            System.out.println(myEvents.toString(count));
            count++;
        }

        EventInterface event = Utils.chooseEventOnList(events);
        if(event == null) {
            return true;
        }

        System.out.println("----Criar sub-evento----");
        System.out.print("Nome do sub-evento: ");
        String name = sc.nextLine();
        System.out.print("Descrição do sub-evento: ");
        String description = sc.nextLine();
        System.out.print("Data do sub-evento: ");
        String date = Utils.validateDate();
//        System.out.println("Hora do sub-evento");
        String hour = Utils.validateHour();
        System.out.print("Local do sub-evento: ");
        String local = sc.nextLine();
        System.out.print("Palestrante do sub-evento: ");
        String speaker = sc.nextLine();
        SubEventInterface subEvent = SubEventController.createSubEvent(event, name, date, local, hour,description,speaker);
        System.out.printf("Sub-evento %s criado\n", subEvent.getName());
        return true;
    }

    private static boolean editSubEventMenu(UserInterface user) {
        System.out.println("----Editar sub-evento----");
        List<EventInterface> events = EventController.getAllEventsByUser(user.getCPF());

        if (events.isEmpty()) {
            System.out.println("Você não tem nenhum evento.");
            return true;
        }

        int count = 0;
        for (EventInterface myEvents : events) {
            System.out.println(myEvents.toString(count));
            count++;
        }
        EventInterface eventChosen = Utils.chooseEventOnList(events);

        if (eventChosen == null) {
            return true;
        }

        ArrayList<SubEventInterface> mySubEvents = SubEventController.subEventsByEvent(eventChosen.getId());

        int cont = 0;
        for(SubEventInterface subEvent : mySubEvents) {
            System.out.println(subEvent.toString(cont));
            cont++;
        }

        SubEventInterface subEvent = Utils.chooseSubEventOnList(mySubEvents);

        if (subEvent == null) {
            return true;
        }

        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Digite: " +
                    "\n[1] - Editar nome do sub-evento" +
                    "\n[2] - Editar data" +
                    "\n[3] - Editar local" +
                    "\n[4] - Editar descrição" +
                    "\n[5] - Editar palestrante" +
                    "\n[6] - Voltar");

            System.out.print("Escolha sua opção: ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    isRunning = editSubEventName(subEvent.getId());
                    break;
                case "2":
                    isRunning = editSubEventDate(subEvent.getId());
                    break;
                case "3":
                    isRunning = editSubEventLocal(subEvent.getId());
                    break;
                case "4":
                    isRunning = editSubEventDescription(subEvent.getId());
                    break;
                case "5":
                    isRunning = editSubEventSpeaker(subEvent.getId());
                    break;
                case "6":
                    return true;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
        }
        return true;
    }

    private static boolean editSubEventName(String id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o novo nome do sub-evento: ");
        String name = sc.nextLine();

        boolean newName = SubEventController.editSubEventName(id, name);
        System.out.println("Nome do sub-evento alterado!");

        return newName;
    }

    private static boolean editSubEventDate(String id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a nova data do sub-evento: ");
        String date = Utils.validateDate();

        boolean newDate = SubEventController.editSubEventDate(id, date);
        System.out.println("Data do sub-evento alterada!");

        return newDate;
    }

    private static boolean editSubEventLocal(String id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o novo local do sub-evento: ");
        String local = sc.nextLine();

        boolean newLocal = SubEventController.editSubEventLocal(id, local);
        System.out.println("Local do sub-evento alterado!");

        return newLocal;
    }

    private static boolean editSubEventDescription(String id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a nova descrição do sub-evento: ");
        String description = sc.nextLine();

        boolean newDescription = SubEventController.editSubEventDescription(id, description);
        System.out.println("Descrição do sub-evento alterada!");

        return newDescription;
    }

    private static boolean editSubEventSpeaker(String id) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o novo palestrante do sub-evento: ");
        String speaker = sc.nextLine();

        boolean newSpeaker = SubEventController.editSubEventSpeaker(id, speaker);
        System.out.println("Palestrante do sub-evento alterado!");

        return newSpeaker;
    }

    private static boolean deleteSubEventMenu(UserInterface user) {

        System.out.println("----Remover sub-evento----");
        List<EventInterface> events = EventController.getAllEventsByUser(user.getCPF());

        if (events.isEmpty()) {
            System.out.println("Você não tem nenhum evento.");
            return true;
        }

        int count = 0;
        for (EventInterface myEvents : events) {
            System.out.println(myEvents.toString(count));
            count++;
        }

        EventInterface eventChosen = Utils.chooseEventOnList(events);

        if (eventChosen == null) {
            return true;
        }

        ArrayList<SubEventInterface> mySubEvents = SubEventController.subEventsByEvent(eventChosen.getId());

        int cont = 0;
            for(SubEventInterface subEvent : mySubEvents) {
            System.out.println(subEvent.toString(cont));
            cont++;
        }

        SubEventInterface subEventChosen = Utils.chooseSubEventOnList(mySubEvents);

        if (subEventChosen == null) {
            return true;
        }

        boolean subEventDel = SubEventController.deleteSubEvent(subEventChosen.getId());
        System.out.println("Sub-evento removido!");

        return subEventDel;
    }
}
