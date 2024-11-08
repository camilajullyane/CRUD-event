package org.upe.persistence.JPAUtils;

import jakarta.persistence.EntityManager;

public class EntityManagerFactory {
    private static final EntityManager entityManager;

    static {
        if(isRunningTest()) {
            entityManager = new TestEntityManager().getEntityManager();
        } else {
            entityManager = new DefaultEntityManager().getEntityManager();
        }
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    private static boolean isRunningTest() {
        try {
            Class.forName("org.junit.Test");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
