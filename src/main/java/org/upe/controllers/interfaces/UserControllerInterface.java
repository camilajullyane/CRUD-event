package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;

import java.util.List;

public interface UserControllerInterface {
    boolean deleteAttendeeEvent(String userCPF, String eventID);
    UserInterface getUserByCPF(String cpf);
    List<EventInterface> userEventsIn(String ownerCPF);
    boolean deleteAttendeeFromEvent(UserInterface user, EventInterface event);
    boolean changeEmail(String userEmail, String newEmail);
    boolean changePassword(String cpf, String currentPassword, String newPassword);
}