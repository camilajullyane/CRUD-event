package org.upe.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "certificates")
@Getter
@Setter
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID subEventID;

    private String subEventName;

    private LocalDate subEventDate;

    private String userName;

    public Certificate() {}

    private Certificate(UUID subEventID, String subEventName, LocalDate subEventDate, String userName) {
        this.subEventID = subEventID;
        this.subEventName = subEventName;
        this.subEventDate = subEventDate;
        this.userName = userName;
    }

    public static class CertificadoBuilder {
        private UUID subEventID;
        private String subEventName;
        private LocalDate subEventDate;
        private String userName;

        public CertificadoBuilder withSubEventID(UUID subEventID) {
            this.subEventID = subEventID;
            return this;
        }

        public CertificadoBuilder withSubEventName(String subEventName) {
            this.subEventName = subEventName;
            return this;
        }

        public CertificadoBuilder withSubEventDate(LocalDate subEventDate) {
            this.subEventDate = subEventDate;
            return this;
        }

        public CertificadoBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Certificate build() {
            return new Certificate(subEventID, subEventName, subEventDate, userName);
        }
    }

    public static CertificadoBuilder Builder() {
        return new CertificadoBuilder();
    }
}
