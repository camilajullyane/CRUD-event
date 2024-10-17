package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class CreateEventCotroller {

    @FXML
    private Button certificateButton;

    @FXML
    private StackPane createEventPage;

    @FXML
    private DatePicker eventBeginDate;

    @FXML
    private TextField eventDescription;

    @FXML
    private DatePicker eventEndDate;

    @FXML
    private TextField eventLocation;

    @FXML
    private TextField eventName;

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
    void handleScheduleButton(ActionEvent event) {

    }

    @FXML
    void handleSubscription(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

}
