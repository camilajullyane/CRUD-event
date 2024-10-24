package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.upe.controllers.UserController;
import org.upe.persistence.interfaces.SubEventInterface;

import java.util.List;

public class SubEventController {

    UserController userController = new UserController();
    SubEventController subEventController = new SubEventController();

    @FXML
    StackPane schedulePage;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private Button logOutButton;

    @FXML
    private Button scheduleButton;

    @FXML
    private StackPane subEventPage;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Text userName;

    @FXML
    void backToCertificate(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @FXML
    void moveToHomeScreen(ActionEvent event) {

    }

    @FXML
    void moveToScheduleScreen(ActionEvent event) {

    }

    @FXML
    void moveToSubmissionsPage(ActionEvent event) {

    }

    @FXML
    void moveToSubscriptionButton(ActionEvent event) {

    }

//    private List<SubEventInterface> showSubEvents() {
//
//        List<SubEventInterface> subEvents = subEventController.showSubEvents();
//
//        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
//        VBox mainContainer = new VBox();
//
//        mainContainer.getChildren().clear();
//        mainContainer.setSpacing(10);
//        mainContainer.setAlignment(Pos.CENTER);
//
//        subEvents.forEach(event -> {
//            VBox eventContainer = new VBox();
//            eventContainer.setPrefWidth(490);
//            eventContainer.setPrefHeight(300);
//            eventContainer.setStyle("-fx-background-color: rgba(217, 217, 217, 0.1); -fx-background-radius: 25px;");
//            eventContainer.setSpacing(20);
//            eventContainer.setPadding(new Insets(20, 20, 20, 20));
//
//            Label title = new Label(subEvents.getName());
//            title.setFont(Font.font("Arial", 18));
//            title.setTextFill(Color.WHITE);
//            title.setAlignment(Pos.TOP_CENTER);
//            VBox.setVgrow(title, Priority.ALWAYS);
//            title.setMaxWidth(Double.MAX_VALUE);
//            title.setAlignment(Pos.CENTER);
//
//
//            Label description = new Label(subEvents.getDescription());
//            description.setFont(Font.font("Arial", 14));
//            description.setTextFill(Color.WHITE);
//            description.setWrapText(true);
//
//
//            Label dateLabel = new Label(subEvents.getDate());
//            dateLabel.setFont(Font.font("Arial", 14));
//            dateLabel.setTextFill(Color.WHITE);
//
//
//            Button signUpButton = new Button("Realizar Inscrição");
//            signUpButton.setStyle("-fx-background-radius: 25; -fx-background-color: #2DD4BF; -fx-text-fill: WHITE;");
//            signUpButton.setFont(Font.font("Arial", 14));
//            signUpButton.setOnAction(e -> signUpEvent(event));
//
//
//            Label locationLabel = new Label(subEvents.getLocal());
//            locationLabel.setFont(Font.font("Arial", 14));
//            locationLabel.setTextFill(Color.WHITE);
//
//            eventContainer.getChildren().addAll(title, description, dateLabel, locationLabel, signUpButton);
//            mainContainer.getChildren().add(eventContainer);
//        });
//
//        scrollPane.setContent(mainContainer);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
//        return subEvents;
//    }
//
}
