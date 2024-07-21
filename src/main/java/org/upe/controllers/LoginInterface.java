package org.upe.controllers;

public interface LoginInterface {
    boolean loginUser(String CPF);
    void singUpUser(String name, String CPF, String email);
}
