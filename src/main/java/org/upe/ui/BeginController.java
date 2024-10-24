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
    Button createEventButton;

    @FXML
    Button createAicleButton;

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
    private void moveToSubscriptionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", beginPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", beginPage);
    }

    @FXML
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Programação", beginPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Programação", beginPage);
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", beginPage);
    }

    @FXML
    private void createNewEvent() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCriandoEvento.fxml", "Criar Evento", beginPage);
    }

    @FXML
    private void createNewArticle() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCriandoArtigo.fxml", "Criar Artigo", beginPage);
    }
}
