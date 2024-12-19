package org.upe.persistence.dao;

import jakarta.persistence.EntityManager;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.model.Certificate;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();
    private final Logger logger = Logger.getLogger(CertificateDAO.class.getName());
    public UserInterface create(UserInterface user, SubEventInterface subEvent) {
        try {
            Certificate certificate = Certificate.Builder()
                    .withUser(user)
                    .withSubEvent(subEvent)
                    .build();

            entityManager.getTransaction().begin();
            entityManager.persist(certificate);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
