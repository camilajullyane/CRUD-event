package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;
import java.time.LocalDate;

public class CreateSubEventController {
    private final FacadeInterface facade = new Facade();

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
    private DatePicker beginDate;

    @FXML
    private DatePicker endDate;

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
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", createSubEventPage);
    }

    @FXML
    private void handleCreateSubEvent() throws IOException {
        if(subEventName.getText().isEmpty() || subEventDescription.getText().isEmpty() ||
                subEventHour.getText().isEmpty() || subEventLocation.getText().isEmpty() || beginDate.getValue() == null || endDate.getValue() == null) {
            errorMessage.setVisible(true);
            return;
        }

        String name = subEventName.getText();
        String description = subEventDescription.getText();
        LocalDate begin = beginDate.getValue();
        LocalDate end = endDate.getValue();

        EventInterface currentEvent = SceneLoader.getEventData();
        facade.createSubEvent(currentEvent, name, begin, end,description);

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
        beginDate.setValue(null);
        endDate.setValue(null);
        SceneLoader.setEventData(currentEvent);
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
