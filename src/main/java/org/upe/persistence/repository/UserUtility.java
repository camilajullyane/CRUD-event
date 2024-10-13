package org.upe.persistence.repository;

import org.upe.persistence.model.User;
import org.upe.persistence.interfaces.UserInterface;
import java.util.Arrays;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserUtility {
    private static final Logger LOGGER = Logger.getLogger(UserUtility.class.getName());
    protected static String csvFilePath = "DB/user.csv";

    private UserUtility() {
        throw new UnsupportedOperationException("Essa é uma utilityClass e não pode ser instânciada");
    }

    public static void setCsvFilePath(String csvFilePath) {
        UserUtility.csvFilePath = csvFilePath;
    }

    public static List<User> getAllUsers() {
        ArrayList<User> usersArray = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] newUserLine = line.split(",", -1);
                User user = new User(
                        newUserLine[0],
                        newUserLine[1],
                        newUserLine[2],
                        newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4],
                        newUserLine[5] == null ? "" : newUserLine[5],
                        newUserLine[6] == null ? "" : newUserLine[6]
                );
                usersArray.add(user);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo CSV", e);
        }

        return usersArray;
    }

    private static void updateFileData(List<User> newData) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(csvFilePath))) {
            write.write("name,email,cpf,password,attendeeOn,ownerOf,articleID\n");
            for (User user : newData) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s%n",
                        user.getName(),
                        user.getEmail(),
                        user.getCPF(),
                        user.getPassword(),
                        String.join("#", user.getAttendeeOn()),
                        String.join("#", user.getOwnerOf()),
                        String.join("#", user.getArticleID()));
                write.write(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo CSV", e);
        }
    }

    public static User findByCPF(String cpf) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] newUserLine = line.split(",", -1);
                User user = new User(
                        newUserLine[0],
                        newUserLine[1],
                        newUserLine[2],
                        newUserLine[3],
                        newUserLine[4] == null ? "" : newUserLine[4],
                        newUserLine[5] == null ? "" : newUserLine[5],
                        newUserLine[6] == null ? "" : newUserLine[6]);

                if (user.getCPF().equals(cpf)) {
                    return user;
                }
            }
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo CSV", e);
        }
        return null;
    }

    public static UserInterface createUser(String name, String email, String cpf, String password) {

        if(findByCPF(cpf) != null) {
            return null;
        }

        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            String newLine = String.format("%s,%s,%s,%s,,,", name, email, cpf, password);

            writer.append(System.lineSeparator());
            writer.append(newLine);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo CSV", e);
        }
        return new User(name, cpf, email, "", "","", password);
    }

    public static void updateUserEmail(String cpf, String newEmail) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.setEmail(newEmail);
            }
        }

        updateFileData(users);
    }

    public static void deleteUser(String cpf) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                users.remove(user);
                break;
            }
        }
        updateFileData(users);
    }

    public static void addAttendeeOnEvent(String cpf, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.addAttendeeOn(eventID);
                break;
            }
        }
        UserUtility.updateFileData(users);
    }

    public static void addOwnerOnEvent(String cpf, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
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

    public static boolean deleteAttendeeEvent(String cpf, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.deleteAttendeeOn(eventID);
                break;
            }
        }
        updateFileData(users);
        return true;
    }

    public static void deleteOwnerOf(String cpf, String eventID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.deleteOwnerOf(eventID);
                break;
            }
        }
        updateFileData(users);
    }

    public static void addUserArticle(String cpf, String articleID) {
        List<User> users = UserUtility.getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.addArticleID(articleID);
                break;
            }
        }

        UserUtility.updateFileData(users);
    }

    public static UserInterface authUser(String cpf, String password) {
        User user = findByCPF(cpf);
        if (user == null) {
            return null;
        }

        if(user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
