package org.upe.persistence.interfaces;

import java.util.List;

public interface UserInterface {
    String getCpf();
    long getId();
    List<EventInterface> getAttendeeOn();
    List<EventInterface> getOwnerOf();
    List<ArticleInterface> getArticles();
    String getEmail();
    String getName();
    void subscribeToEvent(EventInterface event);
    void subscribeToSubEvent(SubEventInterface subEvent);
    void addMyEventAsOwner(EventInterface event);
    void unsubscribeToEvent(EventInterface event);
    void unsubscribeToSubEvent(SubEventInterface subEvent);
    void addArticle(ArticleInterface article);
    void addCertificate(CertificateInterface certificate);
}
