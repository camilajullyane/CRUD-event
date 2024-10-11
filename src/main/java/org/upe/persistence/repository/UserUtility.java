package org.upe.persistence.repository;

import org.upe.persistence.model.User;
import org.upe.persistence.interfaces.UserInterface;
import java.util.Arrays;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserUtility {
    protected static String csvFilePath = "DB/user.csv";

    public static void setCsvFilePath(String csvFilePath) {
        UserUtility.csvFilePath = csvFilePath;
    }

    public static List<User> getAllUsers() {
        ArrayList<User> usersArray = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath)) {
            reader.readLine();  // Skip the first line (header)
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newUserLine = line.split(",", -1);
                User user = new User(
                        newUserLine[0],
                        newUserLine[1],
                        newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4],
                        newUserLine[5] == null ? "" : newUserLine[5]
                );
                usersArray.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usersArray;
    }

    private static void updateFileData(ArrayList<User> newData) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(csvFilePath));
            write.write("name,email,cpf,attendeeOn,ownerOf,articleID\n");
            for (User user : newData) {
                String line = String.format("%s,%s,%s,%s,%s,%s\n",
                        user.getName(),
                        user.getEmail(),
                        user.getCPF(),
                        String.join("#", user.getAttendeeOn()),
                        String.join("#", user.getOwnerOf()),
                        String.join("#", user.getArticleID()));
                write.write(line);
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserInterface findByCPF(String CPF) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
            reader.readLine();
            String headerLine = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newUserLine = line.split(",", -1);
                User user = new User(newUserLine[0],
                        newUserLine[1], newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4],
                        newUserLine[5] == null ? "" : newUserLine[5]);

                if (user.getCPF().equals(CPF)) {
                    reader.close();
                    return user;
                }
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserInterface createUser(String name,  String email, String CPF) {

        if(findByCPF(CPF) != null) {
            return null;
        }
        try {
            String newLine = String.format("%s,%s,%s,,,", name, email,CPF);
            FileWriter writer = new FileWriter(csvFilePath, true);
            writer.append(System.lineSeparator());
            writer.append(newLine);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new User(name, CPF, email, "", "","");
    }

    public static void updateUserEmail(String CPF, String newEmail) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.setEmail(newEmail);
            }
        }

        updateFileData(users);
    }

    public static void deleteUser(String CPF) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                users.remove(user);
                break;
            }
        }
        updateFileData(users);
    }

    public static void addAttendeeOnEvent(String CPF, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.addAttendeeOn(eventID);
                break;
            }
        }
        UserUtility.updateFileData(users);
    }

    public static void addOwnerOnEvent(String CPF, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.addOwnerOf(eventID);
                break;
            }
        }
        UserUtility.updateFileData(users);
    }

    public static boolean deleteAllAttendeesFromEvent(String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (Arrays.asList(user.getAttendeeOn()).contains(eventID)) {
                user.deleteAttendeeOn(eventID);
            }
        }
        updateFileData(users);
        return true;
    }

    public static boolean deleteAttendeeEvent(String CPF, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.deleteAttendeeOn(eventID);
                break;
            }
        }
        updateFileData(users);
        return true;
    }

    public static void deleteOwnerOf(String CPF, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.deleteOwnerOf(eventID);
                break;
            }
        }
        updateFileData(users);
    }

    public static void addUserArticle(String CPF, String articleID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.addArticleID(articleID);
                break;
            }
        }

        UserUtility.updateFileData(users);
    }
}
