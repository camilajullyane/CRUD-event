package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Certificate;
import org.upe.persistence.model.User;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger LOGGER = Logger.getLogger(CertificateDAO.class.getName());

    public UserInterface create(UserInterface user, SubEventInterface subEvent) {
        try {
            Certificate certificate = Certificate.Builder()
                    .withUser(user)
                    .withSubEvent(subEvent)
                    .build();

            entityManager.getTransaction().begin();
            entityManager.persist(Certificate);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
