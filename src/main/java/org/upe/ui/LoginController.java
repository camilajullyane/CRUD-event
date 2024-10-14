package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.upe.controllers.AuthController;
import org.upe.persistence.repository.interfaces.UserInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;

public class LoginController {
    private static final AuthController authController = new AuthController();

    @FXML
    StackPane loginPage;

    @FXML
    Button signInButton;

    @FXML
    TextField cpfField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorMessage;

    @FXML
    Button signUpButton;

   @FXML
    private void handleLogin() throws IOException {
       String cpf = cpfField.getText();
       String password = passwordField.getText();

        UserInterface isLogged = authController.loginUser(cpf, password);

        if(isLogged == null) {
            errorMessage.setText("Credenciais erradas ou não cadastradas");
            errorMessage.setVisible(true);
        } else {
            UserSession.getInstance().setCurrentUser(isLogged);
            SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Principal", loginPage);
        }
    }

    @FXML
    private void moveToSignUp() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCadastro.fxml", "Cadastro", loginPage);
    }
}
