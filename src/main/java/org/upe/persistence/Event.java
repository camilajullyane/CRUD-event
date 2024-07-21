package org.upe.persistence;

import java.util.UUID;

    public class Event implements EventInterface{

        public static void generateID() {
            UUID uuID = UUID.randomUUID();
            String idString = uuID.toString();
            System.out.println(idString);
        }
}
