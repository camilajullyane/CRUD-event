package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.upe.utils.SceneLoader;
import javafx.scene.text.Text;
import org.upe.utils.UserSession;

import java.io.IOException;

public class BeginController {
    @FXML
    Button subscriptionButton;

    @FXML
    StackPane beginPage;

    @FXML
    Button logOutButton;
  
    @FXML
    Text userName;

    @FXML
    private void initialize(){
        if (UserSession.getInstance().getCurrentUser() != null) {
            userName.setText(UserSession.getInstance().getCurrentUser().getName());
        }
    }

    @FXML
    private void handleSubscription() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", beginPage);
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", beginPage);
    }

    @FXML
    private void handleScheduleButton() throws IOException {
        System.out.println("Entrou");
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", beginPage);
    }
}
