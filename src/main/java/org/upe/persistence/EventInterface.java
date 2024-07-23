package org.upe.persistence;

import java.time.LocalTime;
import java.util.Date;

public interface EventInterface {
    String getId();
    void setId(String id);
    String getName();
    void setName(String name);
    Date getData();
    LocalTime getHour();
    void setHour(LocalTime hour);
    String getOrganization();
    void setOrganization(String organization);
    String getDescription();
    void setDescription(String description);

}
