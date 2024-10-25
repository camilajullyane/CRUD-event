package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.upe.utils.SceneLoader;
import java.io.IOException;

public class CertificateControler {

    @FXML
    private StackPane certificatePage;

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", certificatePage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", certificatePage);
    }

    @FXML
    private void moveToSubscriptionButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", certificatePage);
    }

    @FXML
    private void moveToSubmissionsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Inscrições", certificatePage);
    }

    @FXML
    private void moveToOpenCertificatePage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaAbrindoCertificado.fxml", "Inscrições", certificatePage);
    }

    @FXML
    void logOut(ActionEvent event) {


    }


}
