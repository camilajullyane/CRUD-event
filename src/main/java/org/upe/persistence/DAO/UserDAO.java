package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.upe.persistence.JPAUtils.JPAUtils;
import org.upe.persistence.model.User;

public class UserDAO {
    private final EntityManager entityManager = JPAUtils.getEntityManagerFactory();

    public User create(String name, String email, String cpf, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setCpf(cpf);
        user.setPassword(password);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        return user;
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
}
