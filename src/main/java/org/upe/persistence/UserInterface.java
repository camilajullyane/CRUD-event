package org.upe.persistence;

public interface UserInterface {
    String getCPF();
    String[] getAttendeeOn();
    static void deleteAttendeeEvent(String CPF, String eventID) {}
    static void addAttendeeOnEvent(String CPF, String eventID) {}
    String[] getOwnerOf();
    static void deleteOwnerOf(String CPF, String eventID) {}

    static void createUser(String name, String CPF, String email) {}
    static void deleteUser(String CPF) {}
    static void updateUserEmail(String CPF, String newEmail) {}
    static void getAllUser() {}
}
