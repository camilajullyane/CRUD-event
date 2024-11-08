package org.upe.persistence.JPAUtils;

import jakarta.persistence.EntityManager;
import lombok.Getter;

public class EntityManagerFactory {
    @Getter
    private static final EntityManager entityManager;

    static {
        if(isRunningTest()) {
            entityManager = new TestEntityManager().getEntityManager();
        } else {
            entityManager = new DefaultEntityManager().getEntityManager();
        }
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
