package org.upe.persistence.DBStrategy;

import jakarta.persistence.EntityManager;

public interface ConnectionStrategy {
    EntityManager getEntityManager();
    void close();
}
