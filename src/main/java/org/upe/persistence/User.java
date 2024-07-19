package org.upe.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;


public class User implements UserInterface {
    private static final String dbPath = "DB/user.csv";
    private String name;
    private String CPF;
    private String email;
    private String attendeeOn;
    private String ownerOf;

    public User(String name, String email, String CPF, String attendeeOn, String ownerOf) {
        this.CPF = CPF;
        this.email = email;
        this.name = name;
        this.attendeeOn = attendeeOn;
        this.ownerOf = ownerOf;
    }

    public static ArrayList<User> readCSV() {
        ArrayList<User> usersArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbPath));
            String line = reader.readLine();

            while (line != null) {
                line = reader.readLine();
                String[] newUserLine = line.split(",", -1);
                User user = new User(newUserLine[0],
                        newUserLine[1], newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4]);
                usersArray.add(user);
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException e) {
            System.out.println(e);
        }

        return usersArray;
    }

    public static String findByCPF() {
        return "";
    }

    public String[] getAllCPF() {
        return CPF.split(",");
    }

    public void changeEmail() {

    }

    public static void createUser(String name, String CPF, String email) {
        String newLine = String.format("%s,%s,%s,,", name, email,CPF);
        try {
            FileWriter writer = new FileWriter(dbPath, true);
            writer.append(System.lineSeparator());
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCPF() {
        return this.CPF;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }
//    //adicionar formatação
//    public String[] getAttendeeOn() {
//        return attendeeOn;
//    }
//    //adicionar formatação
//    public String[] getOwnerOf() {
//        return ownerOf;
//    }
}

