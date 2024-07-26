package org.upe.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilityTest {

    private static String TEST_CSV_FILE_PATH = "DB/test_user.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Criação de um arquivo CSV temporário para fins de teste
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CSV_FILE_PATH))) {
            writer.write("name,email,cpf,attendeeOn,ownerOf,articleID\n");
            writer.write("John Doe,john@example.com,123456789,Event1#Event2,Event3#Event4,\n");
            writer.write("Jane Smith,jane@example.com,987654321,Event5,,\n");
        }
        // Atualizar o caminho do CSV para os testes
        UserUtility.CSV_FILE_PATH = TEST_CSV_FILE_PATH;
    }


    @Test
    void getAllUsers() {
        ArrayList<User> actualUsers = UserUtility.getAllUsers();

        assertEquals(2, actualUsers.size(), "O número de usuários deve ser o mesmo");

        User user1 = actualUsers.get(0);
        assertEquals("John Doe", user1.getName(), "O nome do primeiro usuário deve ser 'John Doe'");
        assertEquals("john@example.com", user1.getEmail(), "O e-mail do primeiro usuário deve ser 'john@example.com'");
        assertEquals("123456789", user1.getCPF(), "O CPF do primeiro usuário deve ser '123456789'");
        assertEquals("Event1#Event2", String.join("#", user1.getAttendeeOn()), "Os eventos que o primeiro usuário está participando devem ser 'Event1#Event2'");
        assertEquals("Event3#Event4", String.join("#", user1.getOwnerOf()), "Os eventos que o primeiro usuário é dono devem ser 'Event3#Event4'");

        User user2 = actualUsers.get(1);
        assertEquals("Jane Smith", user2.getName(), "O nome do segundo usuário deve ser 'Jane Smith'");
        assertEquals("jane@example.com", user2.getEmail(), "O e-mail do segundo usuário deve ser 'jane@example.com'");
        assertEquals("987654321", user2.getCPF(), "O CPF do segundo usuário deve ser '987654321'");
        assertEquals("Event5", String.join("#", user2.getAttendeeOn()), "Os eventos que o segundo usuário está participando devem ser 'Event5'");
        assertEquals("", String.join("#", user2.getOwnerOf()), "Os eventos que o segundo usuário é dono devem ser ''");
    }


    @Test
    void findByCPF() {
        UserInterface user = UserUtility.findByCPF("123456789");
        assertNotNull(user);
        assertEquals("123456789", user.getCPF());

        UserInterface nonExistentUser = UserUtility.findByCPF("000000000");
        assertNull(nonExistentUser);
    }

    @Test
    void createUser() {
        UserInterface newUser = UserUtility.createUser("Alice", "55555555555", "alice@example.com");
        assertNotNull(newUser);
        assertEquals("Alice", newUser.getName());
        assertEquals("55555555555", newUser.getCPF());
        assertEquals("alice@example.com", newUser.getEmail());

    }

    @Test
    void updateUserEmail() {
        UserUtility.updateUserEmail("123456789", "newjohn@example.com");
        UserInterface updatedUser = UserUtility.findByCPF("123456789");
        assertEquals("newjohn@example.com", updatedUser.getEmail());
    }

    @Test
    void deleteUser() {
        UserUtility.deleteUser("123456789");
        UserInterface deletedUser = UserUtility.findByCPF("123456789");
        assertNull(deletedUser);
    }

    @Test
    void deleteAttendeeEvent() {
        UserUtility.deleteAttendeeEvent("123456789", "Event2");
        UserInterface user = UserUtility.findByCPF("123456789");

        assertNotNull(user, "O usuário não deve ser nulo");

        // Converte attendeeOn de array para uma lista de eventos
        List<String> attendeeOnEvents = Arrays.asList(user.getAttendeeOn());
        assertFalse(attendeeOnEvents.contains("Event2"), "O evento deve ser removido da lista de eventos em que o usuário está participando");

        // Verificar que os outros eventos ainda estão presentes
        assertTrue(attendeeOnEvents.contains("Event1"), "O evento Event1 deve permanecer na lista");
    }


}
