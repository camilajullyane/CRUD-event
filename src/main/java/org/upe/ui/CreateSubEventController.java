package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.upe.controllers.SubEventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;

public class CreateSubEventController {

    @FXML
    private StackPane createSubEventPage;

    @FXML
    private TextField subEventName;

    @FXML
    private TextField subEventDescription;

    @FXML
    private TextField subEventHour;

    @FXML
    private TextField subEventLocation;

    @FXML
    private TextField subEventSpeaker;

    @FXML
    Label errorMessage;

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", createSubEventPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", createSubEventPage);
    }

    @FXML
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissão.fxml", "Submissões", createSubEventPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", createSubEventPage);
    }

    @FXML
    private void handleCreateSubEvent() {
        if(subEventName.getText().isEmpty() || subEventDescription.getText().isEmpty() ||
                subEventHour.getText().isEmpty() || subEventLocation.getText().isEmpty() || subEventSpeaker.getText().isEmpty()) {
            errorMessage.setVisible(true);
            return;
        }

        SubEventController subEventController = new SubEventController();

        String name = subEventName.getText();
        String description = subEventDescription.getText();
        String hour = subEventHour.getText();
        String location = subEventLocation.getText();
        String speaker = subEventSpeaker.getText();

        EventInterface currentEvent = SceneLoader.getEventData();
        subEventController.createSubEvent(currentEvent.getId(), name, location, hour, description, speaker);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("SubEvento criado com sucesso!");
        alert.showAndWait();

        subEventName.setText("");
        subEventDescription.setText("");
        subEventHour.setText("");
        subEventLocation.setText("");
        subEventSpeaker.setText("");

    }
    @FXML
    private void handleLogOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", createSubEventPage);
    }

    @FXML
    private void moveToSubscriptionButton () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", createSubEventPage);
    }
}
