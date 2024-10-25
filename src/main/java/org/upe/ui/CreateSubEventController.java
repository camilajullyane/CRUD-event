package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.upe.controllers.SubEventController;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;

public class CreateSubEventController {

    @FXML
    private Button certificateButton;

    @FXML
    private StackPane createSubEventPage;

    @FXML
    private TextField subEventName;

    @FXML
    private TextField subEventParentEventID;

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
    private Button logOutButton;

    @FXML
    private Button publishSubEvent;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private Button subscriptionButton;

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", createSubEventPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Programação", createSubEventPage);
    }

    @FXML
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Programação", createSubEventPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Programação", createSubEventPage);
    }

    @FXML
    private void handleCreateSubEvent() {
        if(subEventName.getText().isEmpty() || subEventParentEventID.getText().isEmpty() || subEventDescription.getText().isEmpty() ||
                subEventHour.getText().isEmpty() || subEventLocation.getText().isEmpty() || subEventSpeaker.getText().isEmpty()) {
            errorMessage.setVisible(true);
            return;
        }

        SubEventController subEventController = new SubEventController();

        String name = subEventName.getText();
        String parentEventID = subEventParentEventID.getText();
        String description = subEventDescription.getText();
        String hour = subEventHour.getText();
        String location = subEventLocation.getText();
        String speaker = subEventSpeaker.getText();

        subEventController.createSubEvent(parentEventID, name, hour , location, description, speaker);

        subEventName.setText("");
        subEventDescription.setText("");
        subEventLocation.setText("");
        subEventSpeaker.setText("");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("SubEvento criado com sucesso!");
        alert.showAndWait();
    }
    @FXML
    private void handleLogOut() {

    }

    @FXML
    private void handleSubscription() {

    }
}
