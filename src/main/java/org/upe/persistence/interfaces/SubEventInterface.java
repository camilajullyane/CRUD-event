package org.upe.persistence.interfaces;

import org.upe.persistence.model.Event;

import java.time.LocalDate;
import java.util.UUID;

public interface SubEventInterface {
    UUID getId();
    String getName();
    void setName(String name);
    String getSpeakers();
    void setSpeakers(String speakers);
    String getDescription();
    void setDescription(String description);
    LocalDate getDate();
    void setDate(LocalDate date);
    Event getParentEvent();
    void setParentEvent(Event parentEvent);
}
