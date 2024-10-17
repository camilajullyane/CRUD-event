package org.upe.persistence.interfaces;

public interface EventInterface {
    String getId();
    String getName();
    String getDate();
    String getOrganization();
    String getDescription();
    String[] getAttendeesList();
    String[] getArticleList();
    String getLocal();
    String getOwnerCPF();
}
