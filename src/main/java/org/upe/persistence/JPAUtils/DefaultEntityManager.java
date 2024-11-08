package org.upe.persistence.JPAUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.upe.utils.EnvConfig;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

public class DefaultEntityManager {
    private static EntityManagerFactory entityManagerFactory = null;

    public DefaultEntityManager() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", EnvConfig.get("DB_DRIVER"));
        properties.put("jakarta.persistence.jdbc.url", EnvConfig.get("DB_URL_DEFAULT"));
        properties.put("jakarta.persistence.jdbc.user", EnvConfig.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", EnvConfig.get("DB_PASSWORD"));

        entityManagerFactory = Persistence.createEntityManagerFactory("default", properties);
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
