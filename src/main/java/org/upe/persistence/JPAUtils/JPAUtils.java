package org.upe.persistence.JPAUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.upe.utils.EnvConfig;

import java.util.HashMap;
import java.util.Map;

public class JPAUtils {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", EnvConfig.get("DB_DRIVER"));
        properties.put("jakarta.persistence.jdbc.url", EnvConfig.get("DB_URL"));
        properties.put("jakarta.persistence.jdbc.user", EnvConfig.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", EnvConfig.get("DB_PASSWORD"));

        entityManagerFactory = Persistence.createEntityManagerFactory("teste-jpa", properties);
    }

    static public EntityManager getEntityManagerFactory() {
        return entityManagerFactory.createEntityManager();
    }
}
