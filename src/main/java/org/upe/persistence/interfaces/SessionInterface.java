package org.upe.persistence.interfaces;

import java.time.LocalDate;
import java.util.UUID;

public interface SessionInterface {
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    LocalDate getDate();
    boolean isPrivateSession();
    UUID getId();

}
