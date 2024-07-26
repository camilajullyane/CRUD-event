//package org.upe.ui;
//
//import org.upe.controllers.SubEventController;
//import org.upe.persistence.*;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class MySubEvents {
//    public static boolean subEventMenu(UserInterface user) {
//        Scanner sc = new Scanner(System.in);
//        boolean isRunning = true;
//
//        while (isRunning) {
//            showSubEventMenu();
//            System.out.print("Escolha sua opção: ");
//            String option = sc.nextLine();
//            switch (option) {
//                case "1":
//                    EventInterface event;
//                    isRunning = createSubEventMenu(user, event);
//                    break;
//                case "2":
//                    isRunning = editSubEventMenu(user);
//                    break;
//                case "3":
//                    isRunning = deleteSubEventMenu(user);
//                    break;
//                case "4":
//                    return true;
//                default:
//                    System.out.print("[ERRO] Digite novamente. ");
//            }
//        }
//        return false;
//    }
//
//    private static void showSubEventMenu() {
//        System.out.println("Digite: \n[1] - Criar sub-evento" +
//                "\n[2] - Editar sub-evento" +
//                "\n[3] - Remover sub-evento" +
//                "\n[4] - Voltar ao menu principal");
//    }
//
//    private static boolean createSubEventMenu(UserInterface user, EventInterface event) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("----Criar sub-evento----");
//        System.out.print("ID do evento: ");
//        String id = sc.nextLine();
//        System.out.print("Nome do sub-evento: ");
//        String name = sc.nextLine();
//        System.out.print("Descrição do sub-evento: ");
//        String description = sc.nextLine();
//        System.out.print("Data do sub-evento: ");
//        String date = sc.nextLine();
//        System.out.print("Local do sub-evento: ");
//        String local = sc.nextLine();
//        System.out.print("Palestrante do sub-evento: ");
//        String speaker = sc.nextLine();
//        SubEvent subEvent = SubEventController.createSubEvent(user, event, name, date, local, description, "", speaker);
//        System.out.printf("Sub-evento %s criado\n", subEvent.getName());
//        return true;
//    }
//
//    private static boolean editSubEventMenu(UserInterface user) {
//        System.out.println("----Editar sub-evento----");
//        ArrayList<SubEventInterface> subEvents = SubEventController.showAllSubEvents();
//        int cont = 0;
//
//        if (subEvents.isEmpty()) {
//            System.out.println("Não há sub-eventos para editar.");
//            return true;
//        }
//
//        for (SubEventInterface subEvent : subEvents) {
//            System.out.println(subEvent.toString(cont));
//            cont++;
//        }
//
//        SubEvent subEvent = (SubEvent) Utils.chooseSubEventOnList(subEvents);
//
//        if (subEvent == null) {
//            return true;
//        }
//
//        Scanner sc = new Scanner(System.in);
//        boolean isRunning = true;
//        while (isRunning) {
//            System.out.println("Digite: " +
//                    "\n[1] - Editar nome do sub-evento" +
//                    "\n[2] - Editar data" +
//                    "\n[3] - Editar local" +
//                    "\n[4] - Editar descrição" +
//                    "\n[5] - Editar palestrante" +
//                    "\n[6] - Voltar");
//
//            System.out.print("Escolha sua opção: ");
//            String option = sc.nextLine();
//            switch (option) {
//                case "1":
//                    isRunning = editSubEventName(subEvent.getId());
//                    break;
//                case "2":
//                    isRunning = editSubEventDate(subEvent.getId());
//                    break;
//                case "3":
//                    isRunning = editSubEventLocal(subEvent.getId());
//                    break;
//                case "4":
//                    isRunning = editSubEventDescription(subEvent.getId());
//                    break;
//                case "5":
//                    isRunning = editSubEventSpeaker(subEvent.getId());
//                    break;
//                case "6":
//                    return true;
//                default:
//                    System.out.print("[ERRO] Digite novamente. ");
//            }
//        }
//        return true;
//    }
//
//    private static boolean editSubEventName(String id) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Digite o novo nome do sub-evento: ");
//        String name = sc.nextLine();
//
//        boolean newName = SubEventController.editSubEventName(id, name);
//        System.out.println("Nome do sub-evento alterado!");
//
//        return newName;
//    }
//
//    private static boolean editSubEventDate(String id) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Digite a nova data do sub-evento: ");
//        String date = sc.nextLine();
//
//        boolean newDate = SubEventController.editSubEventDate(id, date);
//        System.out.println("Data do sub-evento alterada!");
//
//        return newDate;
//    }
//
//    private static boolean editSubEventLocal(String id) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Digite o novo local do sub-evento: ");
//        String local = sc.nextLine();
//
//        boolean newLocal = SubEventController.editSubEventLocal(id, local);
//        System.out.println("Local do sub-evento alterado!");
//
//        return newLocal;
//    }
//
//    private static boolean editSubEventDescription(String id) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Digite a nova descrição do sub-evento: ");
//        String description = sc.nextLine();
//
//        boolean newDescription = SubEventController.editSubEventDescription(id, description);
//        System.out.println("Descrição do sub-evento alterada!");
//
//        return newDescription;
//    }
//
//    private static boolean editSubEventSpeaker(String id) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Digite o novo palestrante do sub-evento: ");
//        String speaker = sc.nextLine();
//
//        boolean newSpeaker = SubEventController.editSubEventSpeaker(id, speaker);
//        System.out.println("Palestrante do sub-evento alterado!");
//
//        return newSpeaker;
//    }
//
//    private static boolean deleteSubEventMenu(UserInterface user) {
//        System.out.println("----Remover sub-evento----");
//        ArrayList<SubEventInterface> subEvents = SubEventController.showAllSubEvents();
//        int cont = 0;
//
//        if (subEvents.isEmpty()) {
//            System.out.println("Não há sub-eventos para remover.");
//            return true;
//        }
//
//        for (SubEventInterface subEvent : subEvents) {
//            System.out.println(subEvent.toString(cont));
//            cont++;
//        }
//
//        SubEvent subEvent = (SubEvent) Utils.chooseSubEventOnList(subEvents);
//
//        if (subEvent == null) {
//            return true;
//        }
//
//        boolean subEventDel = SubEventController.deleteSubEvent(subEvent.getId());
//        System.out.println("Sub-evento removido!");
//
//        return subEventDel;
//    }
//}
