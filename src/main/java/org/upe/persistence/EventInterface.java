package org.upe.persistence;

import java.time.LocalTime;
import java.util.Date;

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
  //  String getHour();
   // String getParentEventId();
   // String getSpeakers();
}
