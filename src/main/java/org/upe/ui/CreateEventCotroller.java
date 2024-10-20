package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;

public class CreateEventCotroller {

    @FXML
    private Button certificateButton;

    @FXML
    private StackPane createEventPage;

    @FXML
    private TextField eventName;

    @FXML
    private DatePicker eventBeginDate;

    @FXML
    private TextField eventDescription;

    @FXML
    private DatePicker eventEndDate;

    @FXML
    private TextField eventOrganization;

    @FXML
    private TextField eventLocation;

    @FXML
    private Button logOutButton;

    @FXML
    private Button publishEvent;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private Button subscriptionButton;

    @FXML
    private void moveToScheduleScreem() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", createEventPage);
    }

    @FXML
    private void handleCreateEvent() {
        EventController eventController = new EventController();
        UserSession userSession = UserSession.getInstance();

        String name = eventName.getText();
        String description = eventDescription.getText();
        String location = eventLocation.getText();
        String beginDate = eventBeginDate.getValue().toString();
        String endDate = eventEndDate.getValue().toString();
        String organization = eventOrganization.getText();

        EventInterface event = eventController.createEvent(userSession.getCurrentUser(), name, description, beginDate, location, organization);
    }
    @FXML
    private void handleLogOut() {

    }

    @FXML
    private void handleSubscription() {

    }
}
