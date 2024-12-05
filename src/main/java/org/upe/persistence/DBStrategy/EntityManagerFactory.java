package org.upe.persistence.DBStrategy;

import jakarta.persistence.EntityManager;
import lombok.Getter;

public class EntityManagerFactory {
    @Getter
    private static final EntityManager entityManager;

    static {
        ConnectionStrategy strategy;
        if (isRunningTest()) {
            strategy = new TestConnectionStrategy();
        } else {
            strategy = new DefaultConnectionStrategy();
        }
        entityManager = strategy.getEntityManager();
    }

    private static boolean isRunningTest() {
        try {
            Class.forName("org.junit.jupiter.api.Test");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
