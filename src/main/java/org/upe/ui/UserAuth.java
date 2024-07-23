package org.upe.ui;

import org.upe.controllers.AuthInteface;
import org.upe.controllers.UserAuthController;

import java.util.Scanner;

public class UserAuth {
    public static void mainScreen() {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        System.out.println("BEM VINDO AO EVEN2" +
                "\n1 - para fazer login" +
                "\n2 - para criar conta" +
                "\n3 - para sair");
        while (isRunning) {
            System.out.print("Escolha sua opção: ");
            String userInput = sc.nextLine();

            switch (userInput) {
                case "1":
                    login();
                case "2":
                    singUp();
                case "3":
                    isRunning = false;
                    break;
                default:
                    System.out.print("[ERRO] Digite novamente. ");
            }
        }
    }

    public static void login() {
        AuthInteface auth = new UserAuthController();
        Scanner sc = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Digite seu CPF: ");
            String userInput = sc.nextLine();
            if (userInput.length() != 11) {
                System.out.print("CPF inválido.\n");
            } else if (auth.loginUser(userInput)) {
                isRunning = false;
                System.out.println("Usuário LOGADO");
                System.out.println("[0] - Meus eventos\n[1] - Configurações da conta");
                int option = sc.nextInt();
                switch (option) {
                    case 0:
                        System.out.println("ir pros meus eventos");
                        break;
                    case 1:
                        System.out.println("ir pro perfil");
                        break;
                }
            }
        }
    }

    public static void singUp() {
        AuthInteface auth = new UserAuthController();
        Scanner sc = new Scanner(System.in);
        System.out.println("Criação de conta");

        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();

        System.out.print("Digite seu email: ");
        String email = sc.nextLine();

        String CPF = "";
        while(CPF.length() != 11) {
            System.out.print("Digite seu CPF: ");
            CPF = sc.nextLine();
        }
        auth.singUpUser(name, CPF, email);

    }


}

