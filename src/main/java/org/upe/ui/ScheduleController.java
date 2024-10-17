package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    EventController eventController = new EventController();

    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox mainContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showEvents();
    }

    private void showEvents() {
        List<EventInterface> events = eventController.getAllEvents();

        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);

        events.forEach(event -> {
            VBox eventContainer = new VBox();
            eventContainer.setPrefHeight(250);
            eventContainer.setPrefWidth(400);
            eventContainer.setAlignment(Pos.CENTER);
            eventContainer.setStyle("-fx-background-color: red; -fx-padding: 5; -fx-border-color: #3F3F46; -fx-border-width: 1;");

            Label eventLabel = new Label(event.getName());
            eventLabel.setPrefHeight(100);
            eventLabel.setPrefWidth(300);
            eventLabel.setStyle("-fx-background-color: black; -fx-padding: 5; -fx-border-color: #000000; -fx-border-width: 1;");
            eventContainer.getChildren().add(eventLabel);

            mainContainer.getChildren().add(eventContainer);
        });

        scrollPane.setContent(mainContainer);
    }
}