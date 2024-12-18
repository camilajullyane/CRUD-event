package org.upe.persistence.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public interface SessionInterface {
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    LocalDate getDate();
    boolean isPrivateSession();
    UUID getId();
    String getSpeaker();
    void setSpeaker(String speaker);
    LocalDateTime getBeginHour();
    void setBeginHour(LocalDateTime beginHour);
    LocalDateTime getEndHour();
    void setEndHour(LocalDateTime endHour);
}
