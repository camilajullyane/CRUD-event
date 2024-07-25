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
    String toString();
    String toString(int position);
}
