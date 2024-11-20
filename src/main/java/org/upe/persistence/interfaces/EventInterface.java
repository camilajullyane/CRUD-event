package org.upe.persistence.interfaces;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.upe.persistence.interfaces.UserInterface;

public interface EventInterface {
    UUID getId();
    String getName();
    String getOrganization();
    String getDescription();
    UserInterface getOwner();
    List<UserInterface> getAttendeesList();
    List<SubEventInterface> getSubEvents();
    List<ArticleInterface> getArticles();
    String getLocal();
    void setLocal(String local);
    void setOrganization(String organization);
    void setDescription(String description);
    void setName(String name);
    void setBeginDate(LocalDate newBeginDate);
    void setEndDate(LocalDate newEndDate);
    void addAttendeeOnEvent(UserInterface user);
}
