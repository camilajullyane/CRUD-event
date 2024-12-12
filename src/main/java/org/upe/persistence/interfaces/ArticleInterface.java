package org.upe.persistence.interfaces;

import java.util.List;
import java.util.UUID;

public interface ArticleInterface {
    String getTitle();
    UUID getId();
    UserInterface getUser();
    String getArticleAbstract();
    List<EventInterface> getSubmittedIn();
    void addSubmittedIn(EventInterface event);
}
