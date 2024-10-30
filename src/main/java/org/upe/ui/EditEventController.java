package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditEventController {

    EventController eventController = new EventController();
    EventInterface currentEvent = SceneLoader.getEventData();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static final String EVENT_EDITED_TITLE = "Evento editado";

    @FXML
    private StackPane editEventPage;

    @FXML
    private TextField newEventName;

    @FXML
    private TextField newEventLocal;

    @FXML
    private TextField newEventDescription;

    @FXML
    private TextField newEventOrganization;

    @FXML
    private TextField newEventDate;

    @FXML
    private void handleNameButton() {
        eventController.editEventName(currentEvent.getId(), newEventName.getText());
        newEventName.setText("");
        alert.setTitle(EVENT_EDITED_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Nome editado com sucesso!");
        alert.showAndWait();

    }

    @FXML
    private void handleLocalButton() {
        eventController.editEventLocal(currentEvent.getId(), newEventLocal.getText());
        newEventLocal.setText("");
        alert.setTitle(EVENT_EDITED_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Local editado com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void handleDescriptionButton() {
        eventController.editEventDescription(currentEvent.getId(), newEventDescription.getText());
        newEventDescription.setText("");
        alert.setTitle(EVENT_EDITED_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Descrição editada com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void handleOrganizationButton() {
        eventController.editEventOrganization(currentEvent.getId(), newEventOrganization.getText());
        newEventOrganization.setText("");
        alert.setTitle(EVENT_EDITED_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Organização editada com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void handleDateButton() {
        eventController.editEventDate(currentEvent.getId(), newEventDate.getText());
        newEventDate.setText("");
        alert.setTitle(EVENT_EDITED_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Data editada com sucesso!");
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
    private void moveToEventPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/myEvents.fxml", "Evento", editEventPage);
    }

}
