package org.upe.controllers;

public interface AuthInteface {
    boolean loginUser(String CPF);
    void singUpUser(String name, String CPF, String email);
}
