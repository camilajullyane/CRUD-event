package org.upe.ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

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
                date = DATE_FORMAT.format(dateInstance);
            } catch (ParseException e) {
                System.out.println("Erro de formatação. Digite uma data válida.");
            }
        }
        return date;
    }

    protected static LocalTime validateHour() {
        Scanner sc = new Scanner(System.in);
        final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hour = null;
        while (hour == null) {
            try {
                System.out.print("Digite a hora (): ");
                String hourString = sc.nextLine();
                hour = LocalTime.parse(hourString, TIME_FORMATTER);
                return hour;
            } catch (DateTimeParseException e) {
                System.out.print("Erro de formatação. Digite uma hora válida.");
            }
        }
        return hour;
    }
}
