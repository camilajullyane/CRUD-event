package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.UserInterface;

public interface AuthControllerInterface {
    UserInterface loginUser(String cpf, String password);
    UserInterface signUpUser(String name, String cpf, String email, String password);
}
