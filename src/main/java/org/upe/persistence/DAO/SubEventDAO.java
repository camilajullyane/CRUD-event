package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import org.upe.persistence.JPAUtils.EntityManagerFactory;

public class SubEventDAO {
    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();

}
