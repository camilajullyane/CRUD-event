package org.upe.ui2;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Utils {

    protected static String validateDate() {
        Scanner sc = new Scanner(System.in);
        final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        while (date == null) {
            try {
                System.out.print("Digite a data (dd/MM/yyyy): ");
                String dateString = sc.nextLine();
                Date dateInstance = DATE_FORMAT.parse(dateString);
                Date currentDate = Calendar.getInstance().getTime();
                if (dateInstance.before(currentDate)) {
                    System.out.println("Erro: A data deve ser posterior à data atual.");
                } else {
                    date = DATE_FORMAT.format(dateInstance);
                }
            } catch (ParseException e) {
                System.out.println("Erro de formatação. Digite uma data válida.");
            }
        }
        return date;
    }

    protected static String validateHour() {
        Scanner sc = new Scanner(System.in);
        final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hour = null;
        while (hour == null) {
            try {
                System.out.print("Digite a hora (hh:MM): ");
                String hourString = sc.nextLine();
                hour = LocalTime.parse(hourString, TIME_FORMATTER);
                return hour.toString();
            } catch (DateTimeParseException e) {
                System.out.print("Erro de formatação. Digite uma hora válida.");
            }
        }
        return hour.toString();
    }

    protected static EventInterface chooseEventOnList(List<EventInterface> myEvents) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Escolha um evento: (-1 para voltar) ");
            String input = sc.nextLine();
            if (input.equals("-1")) {
                return null;
            } else if (input.matches("\\d+") || input.equals("0")) { // Verifica se a entrada contém apenas números
                int eventNumber = Integer.parseInt(input);
                if (eventNumber < myEvents.size()) {
                    return myEvents.get(eventNumber);
                } else {
                    System.out.print("[ERRO] Número do evento inválido.");
                }
            } else {
                System.out.println("[ERRO] Entrada inválida. Digite apenas números.");
            }
        }
        return null;
    }

    protected static EventInterface chooseEventToLeave(ArrayList<EventInterface> myEvents) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Qual evento você quer sair? (-1 para voltar) ");
            String input = sc.nextLine();
            if (input.equals("-1")) {
                return null;
            } else if (input.matches("\\d+") || input.equals("0")) {
                int eventNumber = Integer.parseInt(input);
                if (eventNumber < myEvents.size()) {
                    return myEvents.get(eventNumber);
                } else {
                    System.out.print("[ERRO] Número do evento inválido.");
                }
            } else {
                System.out.println("[ERRO] Entrada inválida. Digite apenas números.");
            }
        }
        return null;
    }


    protected static SubEventInterface chooseSubEventOnList(ArrayList<SubEventInterface> subEvents) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Qual sub-evento você quer editar? (-1 para voltar) ");
            String input = sc.nextLine();
            if (input.equals("-1")) {
                return null;
            } else if (input.matches("\\d+")) { // Verifica se a entrada contém apenas números
                int subEventNumber = Integer.parseInt(input);
                if (subEventNumber >= 0 && subEventNumber < subEvents.size()) {
                    return subEvents.get(subEventNumber);
                } else {
                    System.out.println("[ERRO] Número do sub-evento inválido.");
                }
            } else {
                System.out.println("[ERRO] Entrada inválida. Digite apenas números.");
            }
        }
        return null;
    }

    protected static ArticleInterface chooseArticleOnList(ArrayList<ArticleInterface> myArticles) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Qual artigo você quer submeter? (-1 para voltar) ");
            String input = sc.nextLine();
            if (input.equals("-1")) {
                return null;
            } else if (input.matches("\\d+") || input.equals("0")) {
                int eventNumber = Integer.parseInt(input);
                if (eventNumber < myArticles.size()) {
                    return myArticles.get(eventNumber);
                } else {
                    System.out.print("[ERRO] Número do artigo inválido.");
                }
            } else {
                System.out.println("[ERRO] Entrada inválida. Digite apenas números.");
            }
        }
        return null;
    }

}
