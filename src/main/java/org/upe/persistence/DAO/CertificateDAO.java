package org.upe.persistence.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.upe.persistence.DBStrategy.EntityManagerFactory;
import org.upe.persistence.model.Certificate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CertificateDAO {

    private final EntityManager entityManager = EntityManagerFactory.getEntityManager();

    public Certificate create(UUID subEventID, String subEventName, LocalDate subEventDate, String userName) {
        Certificate certificate = Certificate.builder()
                .withSubEventID(subEventID)
                .withSubEventName(subEventName)
                .withSubEventDate(subEventDate)
                .withUserName(userName)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(certificate);
        entityManager.getTransaction().commit();
        return certificate;
    }

    public Certificate findById(UUID id) {
        return entityManager.find(Certificate.class, id);
    }

    public List<Certificate> findByUserName(String userName) {
        String jpql = "SELECT c FROM Certificate c WHERE c.userName = :userName";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        query.setParameter("userName", userName);
        return query.getResultList();
    }

    public List<Certificate> findBySubEventId(UUID subEventID) {
        String jpql = "SELECT c FROM Certificate c WHERE c.subEventID = :subEventID";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        query.setParameter("subEventID", subEventID);
        return query.getResultList();
    }

    public Certificate update(Certificate certificate) {
        entityManager.getTransaction().begin();
        entityManager.merge(certificate);
        entityManager.getTransaction().commit();
        return certificate;
    }

    public void delete(UUID id) {
        Certificate certificate = findById(id);
        if (certificate != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(certificate);
            entityManager.getTransaction().commit();
        }
    }

    public List<Certificate> getAllCertificates() {
        String jpql = "SELECT c FROM Certificate c";
        TypedQuery<Certificate> query = entityManager.createQuery(jpql, Certificate.class);
        return query.getResultList();
    }
}
