package org.upe.persistence.repository.interfaces;

public interface EventInterface {
    String getId();
    String getName();
    String getDate();
    String getOrganization();
    String getDescription();
    String[] getAttendeesList();
    String[] getArticleList();
    String toString();
    String toString(int position);
    String getLocal();
    String getOwnerCPF();
}
