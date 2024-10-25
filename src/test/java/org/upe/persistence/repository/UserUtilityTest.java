package org.upe.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.upe.persistence.model.User;
import org.upe.persistence.interfaces.UserInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilityTest {
    private UserUtility userUtility;

    @BeforeEach
    void setUp() {
        userUtility = new UserUtility();
        userUtility.setCsvFilePath("DB/teste/test_user.csv"); // Use a test CSV file to avoid modifying the real data

        try (FileWriter writer = new FileWriter("DB/teste/test_user.csv")) {
            writer.write("name,email,cpf,password,attendeeOn,ownerOf,articleID\n");
            writer.write("Jane Doe,jane.doe@upe.br,13545345312,jane123+,,,\n");
            writer.write("John Doe,john.doe@example.com,123456789,password,,,\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userUtility.getAllUsers();
        assertNotNull(users, "The user list should not be null");
        assertFalse(users.isEmpty(), "The user list should not be empty");
    }

    @Test
    void testFindByCPF() {
        User user = userUtility.findByCPF("13545345312");
        assertNotNull(user, "The user should not be null");
        assertEquals("Jane Doe", user.getName(), "The user name should match");
    }

    @Test
    void testFindByEmail() {
        User user = userUtility.findByEmail("john.doe@example.com");
        assertNotNull(user, "The user should not be null");
        assertEquals("123456789", user.getCPF(), "The user CPF should match");
    }

    @Test
    void testCreateUser() {
        UserInterface newUser = userUtility.createUser("Test User", "test.user@upe.br", "98765432100", "testpassword");
        assertNotNull(newUser, "The new user should not be null");
        assertEquals("Test User", newUser.getName(), "The user name should match");
    }

    @Test
    void testUpdateUserEmail() {
        boolean result = userUtility.updateUserEmail("jane.doe@upe.br", "new.email@upe.br");
        assertTrue(result, "The email should be updated successfully");
        User user = userUtility.findByCPF("13545345312");
        assertEquals("new.email@upe.br", user.getEmail(), "The user email should match the new email");
    }

    @Test
    void testDeleteUser() {
        userUtility.deleteUser("98765432100");
        User user = userUtility.findByCPF("98765432100");
        assertNull(user, "The user should be null after deletion");
    }

    @Test
    void testAddAttendeeOnEvent() {
        UserInterface userTest = userUtility.findByCPF("123456789");
        userUtility.addAttendeeOnEvent(userTest, "event123");
        User user = userUtility.findByCPF("123456789");
        assertTrue(Arrays.asList(user.getAttendeeOn()).contains("event123"), "The user should be an attendee of the event");
    }

    @Test
    void testDeleteAttendeeEvent() {
        userUtility.deleteAttendeeEvent("123456789", "event123");
        User user = userUtility.findByCPF("123456789");
        assertFalse(Arrays.asList(user.getAttendeeOn()).contains("event123"), "The user should not be an attendee of the event");
    }

    @Test
    void testAuthUser() {
        UserInterface user = userUtility.authUser("123456789", "password");
        assertNotNull(user, "The user should be authenticated successfully");
        assertEquals("John Doe", user.getName(), "The user name should match");
    }

    @Test
    void testCreateUserWithExistingCPF() {
        UserInterface newUser = userUtility.createUser("Jane Doe", "new.email@upe.br", "13545345312", "newpassword");
        assertNull(newUser, "The user should not be created with an existing CPF");
    }

    @Test
    void testCreateUserWithExistingEmail() {
        UserInterface newUser = userUtility.createUser("Jane Doe", "jane.doe@upe.br", "98765432100", "newpassword");
        assertNull(newUser, "The user should not be created with an existing email");
    }

    @Test
    void testCreateUserWithEmptyFields() {
        UserInterface newUser = userUtility.createUser("", "", "", "");
        assertNotNull(newUser, "The user should be created even with empty fields");
        assertEquals("", newUser.getName(), "The user name should be empty");
        assertEquals("", newUser.getEmail(), "The user email should be empty");
        assertEquals("", newUser.getCPF(), "The user CPF should be empty");
    }

    @Test
    void testUpdateUserEmailWithExistingEmail() {
        boolean result = userUtility.updateUserEmail("13545345312", "john.doe@example.com");
        assertFalse(result, "The email should not be updated to an existing email");
    }

    @Test
    void testDeleteUserWithNonExistingCPF() {
        userUtility.deleteUser("00000000000");
        User user = userUtility.findByCPF("00000000000");
        assertNull(user, "The user should be null as it does not exist");
    }

}