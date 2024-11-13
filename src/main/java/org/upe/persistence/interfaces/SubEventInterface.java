package org.upe.persistence.interfaces;

public interface SubEventInterface {
    // Métodos para obter detalhes do sub-evento
    String getId();
    EventInterface getParentEvent();

}
