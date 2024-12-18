package org.upe.controllers;

import org.upe.persistence.DAO.CertificateDAO;
import org.upe.persistence.DAO.SubEventDAO;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.util.List;

public class CertificateController {
    private CertificateDAO certificateDAO;
    private SubEventDAO subEventDAO;

    public CertificateController(CertificateDAO certificateDAO, SubEventDAO subEventDAO) {
        this.certificateDAO = certificateDAO;
        this.subEventDAO = new SubEventDAO();
    }

    public void generateCertificate() {
        List<SubEventInterface> subEventList = subEventDAO.allSubEventsWithoutCertificate();
        subEventList.forEach(subEvent -> {
            subEvent.getSubEventAttendeesList().forEach(user -> certificateDAO.create(user, subEvent));
            subEvent.setIsCertified();
            subEventDAO.update(subEvent);
        });
    }
}
