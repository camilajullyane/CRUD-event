package org.upe.controllers.interfaces;

import org.upe.persistence.interfaces.UserInterface;

public interface AuthControllerInterface {
    public UserInterface loginUser(String cpf, String password);
    public UserInterface signUpUser(String name, String cpf, String email, String password);
}
