package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public UserInterface create(String name, String email, String cpf, String password) {
        try {
            User user = User.builder()
                    .withName(name)
                    .withCpf(cpf)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public User findByCPF(String cpf) {
        try {
            String jpql = "SELECT u FROM User u WHERE u.cpf = :cpf";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserInterface update(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
