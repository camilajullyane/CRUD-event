package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockTransaction;

    @InjectMocks
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
    }

    @Test
    void testCreateUser() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String cpf = "12345678900";
        String password = "password";

        UserInterface user = userDAO.create(name, email, cpf, password);

        assertNotNull(user);
        verify(mockEntityManager).persist(any(User.class));
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCreateUserWithException() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String cpf = "12345678900";
        String password = "password";

        doThrow(new RuntimeException("Exception during persist")).when(mockEntityManager).persist(any(User.class));

        UserInterface user = userDAO.create(name, email, cpf, password);

        assertNull(user);
        verify(mockEntityManager).persist(any(User.class));
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }

    @Test
    void testFindByCPF() {
        String cpf = "12345678900";
        User user = new User();
        user.setCpf(cpf);

        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(user);

        UserInterface foundUser = userDAO.findByCPF(cpf);

        assertNotNull(foundUser);
        assertEquals(cpf, foundUser.getCpf());
        verify(mockEntityManager).createQuery(anyString(), eq(User.class));
        verify(mockQuery).setParameter("cpf", cpf);
        verify(mockQuery).getSingleResult();
    }

    @Test
    void testFindByCPFWithException() {
        String cpf = "12345678900";

        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenThrow(new RuntimeException("Exception during query"));

        UserInterface foundUser = userDAO.findByCPF(cpf);

        assertNull(foundUser);
        verify(mockEntityManager).createQuery(anyString(), eq(User.class));
        verify(mockQuery).setParameter("cpf", cpf);
        verify(mockQuery).getSingleResult();
    }

    @Test
    void testFindByEmail() {
        String email = "john.doe@example.com";
        User user = new User();
        user.setEmail(email);

        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(user);

        UserInterface foundUser = userDAO.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(mockEntityManager).createQuery(anyString(), eq(User.class));
        verify(mockQuery).setParameter("email", email);
        verify(mockQuery).getSingleResult();
    }

    @Test
    void testFindByEmailWithException() {
        String email = "john.doe@example.com";

        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenThrow(new RuntimeException("Exception during query"));

        UserInterface foundUser = userDAO.findByEmail(email);

        assertNull(foundUser);
        verify(mockEntityManager).createQuery(anyString(), eq(User.class));
        verify(mockQuery).setParameter("email", email);
        verify(mockQuery).getSingleResult();
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setCpf("12345678900");
        user.setEmail("john.doe@example.com");

        UserInterface updatedUser = userDAO.update(user);

        assertNotNull(updatedUser);
        verify(mockEntityManager).merge(user);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateUserWithException() {
        User user = new User();
        user.setCpf("12345678900");
        user.setEmail("john.doe@example.com");

        doThrow(new RuntimeException("Exception during merge")).when(mockEntityManager).merge(any(User.class));

        UserInterface updatedUser = userDAO.update(user);

        assertNull(updatedUser);
        verify(mockEntityManager).merge(user);
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).commit();
    }
}