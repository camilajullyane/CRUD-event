package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.controllers.AuthController;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private static final AuthController authController = new AuthController();

    @FXML
    TextField cpfField;

    @FXML
    TextField nameField;

    @FXML
    TextField emailField;

    @FXML
    TextField passwordField;

    @FXML
    Button signInButton;

    @FXML
    StackPane signUpPage;

    @FXML
    Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.requestFocus();
    }

    @FXML
    private void handleSignUp() throws IOException {
        String cpf = cpfField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!isCpfValid(cpf)) {
            errorMessage.setText("CPF em formato inválido");
            errorMessage.setVisible(true);
            return;
        }

        if (!isEmailValid(email)) {
            errorMessage.setText("Email em formato inválido");
            errorMessage.setVisible(true);
            return;
        }

        if (!isPasswordValid(password)) {
            errorMessage.setText("Tamanho de senha inválida");
            errorMessage.setVisible(true);
            return;
        }

        UserInterface isCreated = authController.signUpUser(name, cpf, email, password);

        if(isCreated == null) {
            errorMessage.setText("Usuário já cadastrado");
            errorMessage.setVisible(true);
        } else {
            UserSession.getInstance().setCurrentUser(isCreated);
            SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", signUpPage);
        }

    }

    @FXML
    private void signInPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", signUpPage);
    }

    private boolean isCpfValid(String cpf) {
        return cpf.matches("[0-9]{11}");
    }

    private boolean isEmailValid(String email) {
        return email.matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}
