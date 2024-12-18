package org.upe.controllers;

import org.upe.controllers.interfaces.CertificateControllerInterface;
import org.upe.persistence.DAO.CertificateDAO;
import org.upe.persistence.DAO.SubEventDAO;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.util.List;

public class CertificateController implements CertificateControllerInterface {
    private CertificateDAO certificateDAO;
    private SubEventDAO subEventDAO;

    public CertificateController(CertificateDAO certificateDAO, SubEventDAO subEventDAO) {
        this.certificateDAO = certificateDAO;
        this.subEventDAO = subEventDAO;
    }

    public boolean generateCertificate() {
        List<SubEventInterface> subEventList = subEventDAO.allSubEventsWithoutCertificate();
        if (subEventList == null || subEventList.isEmpty()) {
            return false;
        }
        subEventList.forEach(subEvent -> {
            subEvent.getSubEventAttendeesList().forEach(user -> certificateDAO.create(user, subEvent));
            subEvent.setIsCertified();
            subEventDAO.update(subEvent);
        });
        return true;
    }
}
