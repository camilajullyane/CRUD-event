package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface SessionControllerInterface {
    SessionInterface create(String name, LocalDate date, LocalDateTime beginHour, LocalDateTime endHour, String local, String description, String speaker, SubEventInterface parentSubEvent);
    boolean delete(UUID id);

}
