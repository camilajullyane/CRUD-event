package org.upe.ui;

import org.upe.persistence.UserInterface;

import java.util.Scanner;

public class CreateEvent {
    public static boolean menuCreateEvents(UserInterface user) {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;


        System.out.println("Digite o nome do evento:\n");
        while (isRunning) {
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    break;
            }
        }
        return false;
    }
}
