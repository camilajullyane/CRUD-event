package org.upe.persistence.interfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.upe.persistence.interfaces.UserInterface;

public interface EventInterface {
    UUID getId();
    String getName();
    String getDate();
    String getOrganization();
    String getDescription();
    List<UserInterface> getAttendeesList();
    String getLocal();
    void setLocal(String local);
    void setOrganization(String organization);
    void setDescription(String description);
    void setName(String name);
    void setDate(Date date);
    void setHour(String hour);
}
