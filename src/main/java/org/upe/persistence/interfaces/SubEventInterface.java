package org.upe.persistence.interfaces;

import org.springframework.cglib.core.Local;
import org.upe.persistence.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SubEventInterface {
    UUID getId();
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    LocalDate getBeginDate();
    void setBeginDate(LocalDate beginDate);
    void setIsCertified();
    LocalDate getEndDate();
    boolean isCertified();
    Event getParentEvent();
    void setParentEvent(Event parentEvent);
    void addAttendeeOnSubEvent(UserInterface user);
    List<UserInterface> getSubEventAttendeesList();
    void removeAttendeeOnSubEvent(UserInterface user);
    boolean isPrivateSubEvent();
    List<SessionInterface> getAllSessions();
}
