package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.upe.utils.SceneLoader;

import java.io.IOException;

public class SubmissionsController {

    @FXML
    private Text articleName;

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private AnchorPane articlePane;

    @FXML
    private Text articleText;

    @FXML
    private Button certificateButton;

    @FXML
    private Text date;

    @FXML
    private Text hour;

    @FXML
    private Button logOutButton;

    @FXML
    private Text place;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private StackPane submissionsPage;

    @FXML
    private Button submitArticleButton;

    @FXML
    private Button subscriptionButton;


    @FXML
    void handleSubmitArticle(ActionEvent event) {

    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", submissionsPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", submissionsPage);
    }

    @FXML
    private void moveToSubscriptionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", submissionsPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Inicio", submissionsPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", submissionsPage);
    }
}
