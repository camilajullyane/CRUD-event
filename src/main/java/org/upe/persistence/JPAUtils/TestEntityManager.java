package org.upe.persistence.JPAUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.upe.utils.EnvConfig;

import java.util.HashMap;
import java.util.Map;

public class TestEntityManager {
    private static EntityManagerFactory entityManagerFactory = null;

    public TestEntityManager() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", EnvConfig.get("DB_DRIVER"));
        properties.put("jakarta.persistence.jdbc.url", EnvConfig.get("DB_URL_TEST"));
        properties.put("jakarta.persistence.jdbc.user", EnvConfig.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", EnvConfig.get("DB_PASSWORD"));

        entityManagerFactory = Persistence.createEntityManagerFactory("teste-jpa", properties);
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}