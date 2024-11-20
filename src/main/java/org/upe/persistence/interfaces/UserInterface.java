package org.upe.persistence.interfaces;

import java.util.List;

public interface UserInterface {
    String getCpf();
    List<EventInterface> getAttendeeOn();
    List<EventInterface> getOwnerOf();
    List<ArticleInterface> getArticles();
    String getEmail();
    String getName();
    void subscribeToEvent(EventInterface event);
}
