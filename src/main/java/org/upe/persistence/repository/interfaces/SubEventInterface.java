package org.upe.persistence.repository.interfaces;

public interface SubEventInterface {
    // Métodos para obter detalhes do sub-evento
    String getId();
    String getParentEventID();
    String getName();
    String getDate();
    String getLocal();
    String getDescription();
    String toString();
    String toString(int position);
    String getHour();
    String getSpeakers();
}
