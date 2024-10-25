package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.upe.controllers.UserController;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;

public class SettingsController {
    private static final String RED_TEXT_STYLE = "-fx-text-fill: red";
    private static final String GREEN_TEXT_STYLE = "-fx-text-fill: green";
    UserSession userSession = UserSession.getInstance();
    UserController userController = new UserController();

    @FXML
    public Button scheduleButton;

    @FXML
    public StackPane settingPage;
    
    @FXML
    Button subscriptionButton;

    @FXML
    Button logOutButton;

    @FXML
    Button changePasswordButton;
    
    @FXML
    Button changeEmailButton;
    
    @FXML
    TextField oldEmail;
    
    @FXML
    TextField newEmail;
    
    @FXML
    TextField oldPassword;
    
    @FXML
    TextField newPassword;

    @FXML
    Button deleteButton;

    @FXML
    Label passwordLabel;

    @FXML
    Label emailLabel;
    
    @FXML
    private void initialize(){
    }

    @FXML
    private void moveToSubscriptionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", settingPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", settingPage);
    }

    @FXML
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", settingPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", settingPage);
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", settingPage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", settingPage);
    }

    @FXML
    private void changeEmail() {
        String currentEmail = oldEmail.getText();
        String newEmailAddress = newEmail.getText();

        if (currentEmail.isEmpty() || newEmailAddress.isEmpty()) {
            emailLabel.setText("Preecha todos os campos");
            emailLabel.setStyle(RED_TEXT_STYLE);
            emailLabel.setVisible(true);
            return;
        }

        if (!currentEmail.equals(userSession.getCurrentUser().getEmail())) {
            emailLabel.setText("Email atual incorreto.");
            emailLabel.setStyle(RED_TEXT_STYLE);
            emailLabel.setVisible(true);
            return;
        }

        if (!isEmailValid(newEmailAddress)) {
            emailLabel.setText("Email inválido");
            emailLabel.setStyle(RED_TEXT_STYLE);
            emailLabel.setVisible(true);
            return;
        }

        if(userController.changeEmail(currentEmail, newEmailAddress)) {
            emailLabel.setText("Email alterado com sucesso");
            emailLabel.setStyle(GREEN_TEXT_STYLE);
            emailLabel.setVisible(true);
            userSession.setCurrentUser(userController.getUserByCPF(userSession.getCurrentUser().getCPF()));
        } else {
            emailLabel.setText("Email já cadastrado");
            emailLabel.setStyle(RED_TEXT_STYLE);
            emailLabel.setVisible(true);
        }
    }

    @FXML
    private void changePassword() {
        String currentPassword = oldPassword.getText();
        String newPassword = this.newPassword.getText();

        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            passwordLabel.setText("Preencha todos os campos");
            passwordLabel.setStyle(RED_TEXT_STYLE);
            passwordLabel.setVisible(true);
            return;
        }

        if (!isPasswordValid(newPassword)) {
            passwordLabel.setText("Senha inválida");
            passwordLabel.setStyle(RED_TEXT_STYLE);
            passwordLabel.setVisible(true);
            return;
        }

        if (userController.changePassword(userSession.getCurrentUser().getCPF(), currentPassword, newPassword)) {
            passwordLabel.setText("Senha alterada com sucesso");
            passwordLabel.setStyle(GREEN_TEXT_STYLE);
            passwordLabel.setVisible(true);
            userSession.setCurrentUser(userController.getUserByCPF(userSession.getCurrentUser().getCPF()));
        } else {
            passwordLabel.setText("Senha atual incorreta");
            passwordLabel.setStyle(RED_TEXT_STYLE);
            passwordLabel.setVisible(true);
            return;
        }


        userSession.setCurrentUser(userController.getUserByCPF(userSession.getCurrentUser().getCPF()));
    }


    private boolean isEmailValid(String email) {
        return email.matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

}
