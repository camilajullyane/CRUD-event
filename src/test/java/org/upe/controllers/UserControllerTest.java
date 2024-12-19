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
class UserControllerTest {
    @InjectMocks
    UserController userController;
    @Mock
    UserDAO userDAO;

    private static User mockUser;

    @BeforeAll
    static void setUp() {
        mockUser = User.builder()
                .withCpf("12345678900")
                .withPassword("password")
                .build();
    }

    @Test
    void testGetUserByCPF() {
        when(userDAO.findByCPF(mockUser.getCpf())).thenReturn(mockUser);
        UserInterface result = userController.getUserByCPF(mockUser.getCpf());
        assertNotNull(result);
    }

    @Test
    void testGetUserWithNonExistentCPF() {
        when(userDAO.findByCPF(mockUser.getCpf())).thenReturn(null);
        UserInterface result = userController.getUserByCPF(mockUser.getCpf());
        assertNull(result);
    }

    @Test
    void testChangePassword() {
        when(userDAO.findByCPF(mockUser.getCpf())).thenReturn(mockUser);
        boolean result = userController.changePassword(mockUser, "password", "<PASSWORD>");
        assertTrue(result);
    }

    @Test
    void testChangePasswordWithWrongPassword() {
        when(userDAO.findByCPF(mockUser.getCpf())).thenReturn(mockUser);
        boolean result = userController.changePassword(mockUser, "wrongPassword", "<PASSWORD>");
        assertFalse(result);
    }

    @Test
    void testChangePasswordWithNonExistentCPF() {
        when(userDAO.findByCPF(mockUser.getCpf())).thenReturn(null);
        boolean result = userController.changePassword(mockUser, "password", "<PASSWORD>");
        assertFalse(result);
    }

    @Test
    void testChangeEmail() {
        when(userDAO.findByEmail(mockUser.getEmail())).thenReturn(mockUser);
        boolean result = userController.changeEmail(mockUser.getEmail(), "<EMAIL>");
        assertTrue(result);
    }

    @Test
    void testChangeEmailWithExistentEmail() {
        when(userDAO.findByEmail(mockUser.getEmail())).thenReturn(null);
        boolean result = userController.changeEmail(mockUser.getEmail(), "<EMAIL>");
        assertFalse(result);
    }
}
