package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.upe.controllers.AuthController;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;

public class LoginController {

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

        UserInterface isLogged = AuthController.loginUser(cpf, password);

        if(isLogged == null) {
            errorMessage.setText("Credenciais erradas ou não cadastradas");
            errorMessage.setVisible(true);
        } else {
            SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Principal", loginPage);
        }
    }

    @FXML
    private void moveToSignUp() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCadastro.fxml", "Cadastro", loginPage);
    }
}
