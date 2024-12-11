package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import org.upe.controllers.SubEventController;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.utils.SceneLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditSubEventController {
    SubEventController subEventController = new SubEventController();

    SubEventInterface subEvent = SceneLoader.getSubEventData();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private StackPane editEventPage;

    @FXML
    private TextField newSubEventName;

    @FXML
    private TextField newSubEventLocal;

    @FXML
    private TextField newSubEventDescription;

    @FXML
    private TextField newSubEventSpeaker;

    @FXML
    private TextField newSubEventHour;


    @FXML
    private void handleNameButton() {
        subEventController.editSubEventName(subEvent, newSubEventName.getText());
        newSubEventName.setText("");
        alert.setTitle("SubEvento editado");
        alert.setHeaderText(null);
        alert.setContentText("Nome editado com sucesso!");
        alert.showAndWait();

    }

    @FXML
    private void handleDescriptionButton() {
        subEventController.editSubEventDescription(subEvent, newSubEventDescription.getText());
        newSubEventDescription.setText("");
        alert.setTitle("SubEvento editado");
        alert.setHeaderText(null);
        alert.setContentText("Descrição editada com sucesso!");
        alert.showAndWait();
    }


    @FXML
    private void moveToSettingsScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", editEventPage);

    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", editEventPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", editEventPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", editEventPage);
    }

    @FXML
    private void moveToSubscriptionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", editEventPage);
    }

    @FXML
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", editEventPage);
    }

    @FXML
    private void moveToCertificatePage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", editEventPage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/myEvents.fxml", "Meus Eventos", editEventPage);
    }

    @FXML
    private void moveToMySubEventPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/mySubEvents.fxml", "Sub Eventos", editEventPage);
    }

}
