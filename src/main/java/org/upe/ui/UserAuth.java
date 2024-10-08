package org.upe.ui;

import org.upe.controllers.AuthInterface;
import org.upe.persistence.interfaces.UserInterface;


import java.util.Scanner;

public class UserAuth {
    static final String CPF_REGEX = "\\d{11}|\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}";

    protected static void mainScreen() {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nBEM VINDO AO EVEN2\nDigite:\n[1] - Para fazer login\n[2] - Para criar conta\n[3] - Para sair");
            System.out.print("Escolha sua opção: ");
            String userInput = sc.nextLine();
            switch (userInput) {
                case "1":
                    isRunning = login();
                    break;
                case "2":
                    isRunning = signUp();
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
        }
        sc.close();
    }

    protected static boolean login() {
        Scanner sc = new Scanner(System.in);
        UserInterface user;

        while (true) {
            System.out.print("Digite seu CPF (-1 retorna ao menu): ");
            String userInput = sc.nextLine();
            if (userInput.equals("-1")) {
                return true;
            } else if (!userInput.matches(CPF_REGEX)) {
                System.out.print("Formato inválido de CPF.");
            } else if ((user = AuthInterface.loginUser(userInput)) != null) {
                return MainMenu.menu(user);
            } else {
                System.out.println("CPF errado ou não cadastrado.");
                return true;
            }
        }
    }

    public static boolean signUp() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Criação de conta");
        String CPF = "";

        while(!CPF.matches(CPF_REGEX)) {
            System.out.print("Digite seu CPF: ");
            CPF = sc.nextLine();
        }

        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();

        System.out.print("Digite seu email: ");
        String email = sc.nextLine();

        UserInterface signUpValidate = AuthInterface.signUpUser(name, CPF, email);

        if(signUpValidate == null) {
            System.out.println("CPF já cadastrado. Faça login!");
            return true;
        } else {
            return MainMenu.menu(signUpValidate);
        }
    }
}
