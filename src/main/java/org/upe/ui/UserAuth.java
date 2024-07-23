package org.upe.ui;

import org.upe.controllers.AuthInterface;
import org.upe.persistence.UserInterface;

import java.util.Scanner;

public class UserAuth {
    protected static void mainScreen() {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        System.out.println("\nBEM VINDO AO EVEN2\n1 - Para fazer login\n2 - Para criar conta\n3 - Para sair");
        while (isRunning) {
            System.out.print("Escolha sua opção: ");
            String userInput = sc.nextLine();
            switch (userInput) {
                case "1":
                    isRunning = login();
                    if(isRunning) {
                        System.out.println("\nBEM VINDO AO EVEN2\n1 - para fazer login\n2 - para criar conta\n3 - para sair");
                    }
                    break;
                case "2":
                    isRunning = signUp();
                    if(isRunning) {
                        System.out.println("\nBEM VINDO AO EVEN2\n1 - para fazer login\n2 - para criar conta\n3 - para sair");
                    }
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
            System.out.print("Digite seu CPF: ");
            String userInput = sc.nextLine();
            if (userInput.length() != 11) {
                System.out.print("Formato inválido de CPF. ");

            } else if ((user = AuthInterface.loginUser(userInput)) != null) {
                return MainMenu.menu(user);
            }
        }
    }

    public static boolean signUp() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Criação de conta");
        String CPF = "";

        while(CPF.length() != 11) {
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

