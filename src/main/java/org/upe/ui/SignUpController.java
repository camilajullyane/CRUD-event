package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.controllers.AuthController;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;

public class SignUpController {
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

    @FXML
    private void handleSignUp() throws IOException {
        String cpf = cpfField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        UserInterface isCreated = AuthController.signUpUser(name, cpf, email, password);

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
}
