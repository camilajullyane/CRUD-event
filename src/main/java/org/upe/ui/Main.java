package org.upe.ui;

import org.upe.controllers.Login;
import org.upe.controllers.LoginInterface;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginInterface login = new Login();
        boolean isRunning = true;
        System.out.println("BEM VINDO AO EVEN2\nEntre com seu CPF para fazer login ou digite 0 para sair");
        while (isRunning) {
            System.out.print("CPF: ");
            String userInput = sc.nextLine();

            switch (userInput) {
                case "0":
                    isRunning = false;
                    break;
                default:
                    if (userInput.length() != 11) {
                        System.out.print("CPF inválido. ");
                    } else if(login.loginUser(userInput)) {
                        System.out.println("Usuário LOGADO");
                    } else {
                        System.out.println("Usuário não está cadastrado ou o CPF foi digitado errado.");
                    }
            }
        }
    }
}