package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.upe.utils.SceneLoader;
import java.io.IOException;

public class CertificateControler {
    @FXML
    public Button settingsButton;

    @FXML
    private StackPane certificatePage;

    @FXML
    Button myEventsButton;


    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", certificatePage);
    }

    @FXML
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", certificatePage);
    }

    @FXML
    private void moveToScheduleScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", certificatePage);
    }

    @FXML
    private void moveToHomeScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", certificatePage);
    }

    @FXML
    private void moveToSubscriptionButton () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", certificatePage);
    }

    @FXML
    private void moveToSubmissionsPage () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", certificatePage);
    }

    @FXML
    private void moveToOpenCertificatePage () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaAbrindoCertificado.fxml", "Certificado", certificatePage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaMyEvents.fxml", "Configurações", certificatePage);
    }


}
