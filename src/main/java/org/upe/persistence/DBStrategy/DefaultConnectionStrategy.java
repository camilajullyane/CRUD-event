package org.upe.persistence.DBStrategy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.upe.utils.EnvConfig;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

public class DefaultConnectionStrategy implements ConnectionStrategy {
    private static EntityManagerFactory entityManagerFactory = null;

    public DefaultConnectionStrategy() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", EnvConfig.get("DB_DRIVER"));
        properties.put("jakarta.persistence.jdbc.url", EnvConfig.get("DB_URL_DEFAULT"));
        properties.put("jakarta.persistence.jdbc.user", EnvConfig.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", EnvConfig.get("DB_PASSWORD"));
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

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
