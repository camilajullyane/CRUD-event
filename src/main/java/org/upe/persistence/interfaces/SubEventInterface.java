package org.upe.persistence.interfaces;

import java.time.LocalDate;

public interface SubEventInterface {
    // Métodos para obter detalhes do sub-evento
    String getId();
    EventInterface getParentEvent();
    LocalDate getDate();

}
