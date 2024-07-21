package org.upe.persistence;

import java.io.*;
import java.util.ArrayList;

public class UserUtility {
    private static final String dbPath = "DB/user.csv";

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> usersArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbPath));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newUserLine = line.split(",", -1);
                User user = new User(newUserLine[0],
                        newUserLine[1], newUserLine[2],
                        newUserLine[3] == null ? "" : newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4]);
                usersArray.add(user);
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return usersArray;
    }

    //public static ArrayList<UserInterface> getAllUsersWhere()

    private static void updateFileData(ArrayList<User> newData) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(dbPath));
            write.write("name,email,cpf,attendeeOn,ownerOf\n");
            for (User user : newData) {
                String line = String.format("%s,%s,%s,%s,%s\n", user.getName(), user.getEmail(),user.CPF, user.attendeeOn, user.ownerOf);
                write.write(line);
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserInterface findByCPF(String CPF) {
        ArrayList<User> users = getAllUsers();
        UserInterface userFound = null;
        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                userFound = new User(user.name, user.email, user.CPF, user.attendeeOn, user.ownerOf);
            }
        }
        if (userFound == null) {
            return null;
        } else {
            return userFound;
        }
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

    public static void updateUserEmail(String CPF, String newEmail) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.email = newEmail;
            }
        }

        updateFileData(users);
    }

    public static void deleteUser(String CPF) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                int index = users.indexOf(user);
                users.remove(index);
                break;
            }
        }

        updateFileData(users);
    }

    public static void addAttendeeOnEvent(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                user.attendeeOn += "#" + eventID;
                break;
            }
        }
        UserUtility.updateFileData(users);
    }

    public static void deleteAttendeeEvent(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                String newString = "";
                for (int i = 0; i < user.getAttendeeOn().length; i++) {
                    String id = user.getAttendeeOn()[i];
                    if (!id.equals(eventID)) {
                        newString += id + "#";
                    }
                }
                user.attendeeOn = newString.substring(0, newString.length() - 1);
                break;
            }
        }
        updateFileData(users);
    }

    public static void deleteOwnerOf(String CPF, String eventID) {
        ArrayList<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(CPF)) {
                String newString = "";
                for (int i = 0; i < user.getOwnerOf().length; i++) {
                    String id = user.getOwnerOf()[i];
                    if (!id.equals(eventID)) {
                        newString += id + "#";
                    }
                }
                user.ownerOf = newString.substring(0, newString.length() - 1);
                break;
            }
        }
        updateFileData(users);
    }
}
