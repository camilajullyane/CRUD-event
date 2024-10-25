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

    public static void setCsvFilePath(String csvFilePath) {
        UserUtility.csvFilePath = csvFilePath;
    }

    public List<User> getAllUsers() {
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

    public void updateFileData(List<User> newData) {
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

    public User findByCPF(String cpf) {
        return getAllUsers().stream().filter(user -> user.getCPF().equals(cpf)).findFirst().orElse(null);
    }

    public User findByEmail(String email) {
        return getAllUsers().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    public UserInterface createUser(String name, String email, String cpf, String password) {

        if(findByCPF(cpf) != null) {
            return null;
        }

        if(findByEmail(email) != null) {
            return null;
        }

        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            String newLine = String.format("%s,%s,%s,%s,,,", name, email, cpf, password);

            writer.append(System.lineSeparator());
            writer.append(newLine);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo CSV", e);
        }
        return new User(name, email, cpf, password, "","", "");
    }

    public boolean updateUserEmail(String cpf, String newEmail) {
        List<User> users = getAllUsers();

        if (findByEmail(newEmail) != null) {
            return false;
        }

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.setEmail(newEmail);
            }
        }

        updateFileData(users);
        return true;
    }

    public boolean updateUserPassword(String cpf, String currentPassword, String newPassword) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf) && user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                updateFileData(users);
                return true;
            }
        }
        return false;
    }

    public void deleteUser(String cpf) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                users.remove(user);
                break;
            }
        }
        updateFileData(users);
    }

    public boolean addAttendeeOnEvent(UserInterface user, String eventID) {
        for (String ownerOf : user.getOwnerOf()) {
            if (ownerOf.equals(eventID)) {
                return false;
            }
        }

        for(String attendeeOn : user.getAttendeeOn()) {
            if(attendeeOn.equals(eventID)) {
                return false;
            }
        }

        List<User> users = getAllUsers();

        for (User userData : users) {
            if (userData.getCPF().equals(user.getCPF())) {
                userData.addAttendeeOn(eventID);
                break;
            }
        }
        updateFileData(users);
        return true;
    }

    public void addOwnerOnEvent(String userCPF, String eventID) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(userCPF)) {
                user.addOwnerOf(eventID);
                break;
            }
        }
        updateFileData(users);
    }

    public boolean deleteAllAttendeesFromEvent(String eventID) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (Arrays.asList(user.getAttendeeOn()).contains(eventID)) {
                user.deleteAttendeeOn(eventID);
            }
        }
        updateFileData(users);
        return true;
    }

    public boolean deleteAttendeeEvent(String cpf, String eventID) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.deleteAttendeeOn(eventID);
                break;
            }
        }
        updateFileData(users);
        return true;
    }

    public void deleteOwnerOf(String cpf, String eventID) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.deleteOwnerOf(eventID);
                break;
            }
        }
        updateFileData(users);
    }

    public void addUserArticle(String cpf, String articleID) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getCPF().equals(cpf)) {
                user.addArticleID(articleID);
                break;
            }
        }

        updateFileData(users);
    }

    public UserInterface authUser(String cpf, String password) {
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
