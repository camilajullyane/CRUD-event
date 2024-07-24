package org.upe.ui;

import org.upe.controllers.AuthInterface;
import org.upe.controllers.UserController;
import org.upe.persistence.User;

import java.util.Scanner;

public class UserAuth {

    private static AuthInterface authController = new UserController();

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
        User user;
        while (true) {
            System.out.print("Digite seu CPF: ");
            String userInput = sc.nextLine();
            if (userInput.length() != 11) {
                System.out.print("Formato inválido de CPF. ");
            } else if ((user = authController.loginUser(userInput)) != null) {
                return MainMenu.menu(user);
            } else {
                System.out.println("CPF não encontrado.");
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

        User signUpValidate = authController.signUpUser(name, CPF, email);

        if(signUpValidate == null) {
            System.out.println("CPF já cadastrado. Faça login!");
            return true;
        } else {
            return MainMenu.menu(signUpValidate);
        }
    }
}
