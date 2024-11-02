package org.upe.persistence.model;

import jakarta.persistence.*;
import org.upe.persistence.interfaces.EventInterface;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf", "email"}))
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}