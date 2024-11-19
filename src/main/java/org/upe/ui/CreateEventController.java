package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.utils.DatePickerUtil;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.time.LocalDate;

public class CreateEventController {
    private final UserSession userSession = UserSession.getInstance();
    private final FacadeInterface facade = new Facade();

    @FXML
    public Button settingsButton;

    @FXML
    public Button myEventsButton;

    @FXML
    private StackPane createEventPage;

    @FXML
    private TextField eventName;

    @FXML
    private DatePicker eventBeginDate;

    @FXML
    private DatePicker eventEndDate;


    @FXML
    private TextField eventDescription;

    @FXML
    private TextField eventOrganization;

    @FXML
    private TextField eventLocation;

    @FXML
    Label errorMessage;


    @FXML
    public void initialize() {
        DatePickerUtil.restrictDatePicker(eventBeginDate);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", createEventPage);
    }

    @FXML
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", createEventPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", createEventPage);
    }

    @FXML
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telasubmissões.fxml", "Submissões", createEventPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", createEventPage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/myEvents.fxml", "Meus eventos", createEventPage);
    }

    @FXML
    private void handleCreateEvent() {
        if(eventName.getText().isEmpty() || eventDescription.getText().isEmpty() || eventLocation.getText().isEmpty() ||
                eventBeginDate.getValue() == null || eventOrganization.getText().isEmpty()) {
            errorMessage.setVisible(true);
            return;
        }


        String name = eventName.getText();
        String description = eventDescription.getText();
        String location = eventLocation.getText();
        LocalDate beginDate = eventBeginDate.getValue();
        LocalDate endDate = eventEndDate.getValue();
        String organization = eventOrganization.getText();

        facade.createEvent(userSession.getCurrentUser(), name, description, beginDate, endDate, location, organization);
        UserSession.getInstance().setCurrentUser(facade.getUserByCPF(UserSession.getInstance().getCurrentUser().getCpf()));

        eventName.setText("");
        eventDescription.setText("");
        eventLocation.setText("");
        eventBeginDate.setValue(null);
        eventEndDate.setValue(null);
        eventOrganization.setText("");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("Evento criado com sucesso!");
        alert.showAndWait();
    }
    @FXML
    private void handleLogOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", createEventPage);
    }

    @FXML
    private void moveToSubscriptionButton () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", createEventPage);
    }
}
