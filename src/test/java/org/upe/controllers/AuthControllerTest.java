package org.upe.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.upe.persistence.dao.UserDAO;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserDAO userDAO;

    private static User mockUser;
    private static User fullDataUser;

    @BeforeAll
    static void setUp() {
        mockUser = User.builder()
                .withCpf("12345678900")
                .withPassword("password")
                .build();

        fullDataUser = User.builder()
                .withName("Batman")
                .withEmail("bruce@batmail.bat")
                .withCpf("12345678910")
                .withPassword("password")
                .build();
    }

    @Test
    void testLoginUser() {
        when(userDAO.findByCPF("12345678900")).thenReturn(mockUser);

        UserInterface result = authController.loginUser("12345678900", "password");
        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
    }

    @Test
    void testLoginUserWithWrongPassword() {
        when(userDAO.findByCPF("12345678900")).thenReturn(mockUser);
        UserInterface result = authController.loginUser("12345678900", "ssapdrow");
        assertNull(result);
    }

    @Test
    void testLoginUserWithWrongCPF() {
        when(userDAO.findByCPF("12345678130")).thenReturn(null);
        UserInterface result = authController.loginUser("12345678130", "ssapdrow");
        assertNull(result);
    }

    @Test
    void testSingUp() {
        when(userDAO.create(fullDataUser.getName(), fullDataUser.getEmail(), fullDataUser.getCpf(), fullDataUser.getPassword())).thenReturn(fullDataUser);
        UserInterface result = authController.signUpUser(fullDataUser.getName(), fullDataUser.getCpf(), fullDataUser.getEmail(), fullDataUser.getPassword());
        assertNotNull(result);
    }

    @Test
    void testSingUpWithExistingCPF() {
        when(userDAO.findByCPF(fullDataUser.getCpf())).thenReturn(fullDataUser);
        UserInterface result = authController.signUpUser(fullDataUser.getName(), fullDataUser.getCpf(), fullDataUser.getEmail(), fullDataUser.getPassword());
        assertNull(result);
    }

    @Test
    void testSingUpWithExistingEmail() {
        when(userDAO.findByEmail(fullDataUser.getEmail())).thenReturn(fullDataUser);
        UserInterface result = authController.signUpUser(fullDataUser.getName(), fullDataUser.getCpf(), fullDataUser.getEmail(), fullDataUser.getPassword());
    }
}
