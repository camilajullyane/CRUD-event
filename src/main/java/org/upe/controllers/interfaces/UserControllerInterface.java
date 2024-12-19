package org.upe.controllers.interfaces;
import org.upe.persistence.interfaces.UserInterface;

public interface UserControllerInterface {
    UserInterface getUserByCPF(String cpf);
    boolean changeEmail(String userEmail, String newEmail);
    boolean changePassword(UserInterface user, String currentPassword, String newPassword);
}