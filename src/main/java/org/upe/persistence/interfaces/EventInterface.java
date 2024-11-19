package org.upe.persistence.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EventInterface {
    UUID getId();
    String getName();
    LocalDate getBeginDate();
    LocalDate getEndDate();
    String getOrganization();
    String getDescription();
    List<UserInterface> getAttendeesList();
    String getLocal();
    void setLocal(String local);
    void setOrganization(String organization);
    void setDescription(String description);
    void setName(String name);
    void setBeginDate(LocalDate newBeginDate);
    void setEndDate(LocalDate newEndDate);
}
