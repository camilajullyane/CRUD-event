package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;

public class SettingsController {
    UserSession userSession = UserSession.getInstance();

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
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Programação", settingPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Programação", settingPage);
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", settingPage);
    }

    @FXML
    private void changeEmail() {
        String currentEmail = oldEmail.getText();
        String newEmailAddress = newEmail.getText();

        if (currentEmail.isEmpty() || newEmailAddress.isEmpty()) {
            emailLabel.setText("os campos não podem estar vazios");
            emailLabel.setVisible(true);
            return;
        }

        if (!currentEmail.equals(userSession.getCurrentUser().getEmail())) {
            emailLabel.setText("O email atual não corresponde ao email do usuário.");
            emailLabel.setVisible(true);
            return;
        }
    }

    @FXML
    private void setChangePasswordButton() {

    }

}
