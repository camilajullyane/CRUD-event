package org.upe.persistence.interfaces;

import java.util.List;

public interface ArticleInterface {
    String getTitle();
    String getId();
    UserInterface getUser();
    String getArticleAbstract();
    List<EventInterface> getSubmittedIn();
}
