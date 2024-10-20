package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubscriptionController implements Initializable {

    EventController eventController = new EventController();

    @FXML
    ScrollPane subscriptionScroll;

    @FXML
    Button homeButton;

    @FXML
    Button logOutButton;

    @FXML
    Button scheduleButton;

    @FXML
    StackPane mySubscriptionPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showMyEvents();
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", mySubscriptionPage);
    }

    @FXML
    private void homePage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", mySubscriptionPage);
    }

    @FXML
    private void schedulePage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", mySubscriptionPage);
    }

    @FXML
    private void showMyEvents() {
        System.out.println("showMyEvents called");

        List<EventInterface> events = eventController.getAllEventsByUser(UserSession.getInstance().getCurrentUser().getCPF());
        System.out.println("Number of events: " + events.size());

        subscriptionScroll.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);

        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.setPrefWidth(150);
            eventContainer.setPrefHeight(160);
            eventContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
            eventContainer.setSpacing(10);
            eventContainer.setPadding(new Insets(10, 10, 10, 10));
            eventContainer.setAlignment(Pos.CENTER);

            Label title = new Label("Você não tem eventos inscritos");
            title.setFont(Font.font("Roboto", 14));
            title.setTextFill(Color.WHITE);
            VBox.setVgrow(title, Priority.ALWAYS);
            title.setMaxWidth(Double.MAX_VALUE);
            title.setAlignment(Pos.CENTER);

            eventContainer.getChildren().add(title);

            mainContainer.setPadding(new Insets(20, 20, 20, 20));
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(eventContainer);
        } else {
            events.forEach(event -> {
                VBox eventContainer = new VBox();
                eventContainer.setPrefWidth(557);
                eventContainer.setPrefHeight(141);
                eventContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
                eventContainer.setSpacing(10);
                eventContainer.setPadding(new Insets(10, 10, 10, 10));

                Label titleLabel = new Label(event.getName());
                titleLabel.setPrefHeight(40);
                titleLabel.setPrefWidth(133);
                titleLabel.setTextFill(Color.web("#2dd4bf"));
                titleLabel.setFont(Font.font("System Bold Italic", 16));

                Label descriptionLabel = new Label(event.getDescription());
                descriptionLabel.setPrefHeight(20);
                descriptionLabel.setPrefWidth(133);
                descriptionLabel.setTextFill(Color.web("#cdc7c7"));

                Label startDateLabel = new Label("Data Inicial");
                startDateLabel.setPrefHeight(17);
                startDateLabel.setPrefWidth(89);
                startDateLabel.setTextFill(Color.WHITE);
                startDateLabel.setFont(Font.font("System Bold Italic", 12));

                Label startDateValue = new Label(event.getDate());
                startDateValue.setPrefHeight(20);
                startDateValue.setPrefWidth(89);
                startDateValue.setTextFill(Color.web("#cdc7c7"));

                Label endDateLabel = new Label("Data Final");
                endDateLabel.setPrefHeight(17);
                endDateLabel.setPrefWidth(89);
                endDateLabel.setTextFill(Color.WHITE);
                endDateLabel.setFont(Font.font("System Bold Italic", 12));

                Label endDateValue = new Label(event.getDate());
                endDateValue.setPrefHeight(20);
                endDateValue.setPrefWidth(89);
                endDateValue.setTextFill(Color.web("#cdc7c7"));
                endDateValue.setFont(Font.font("System Italic", 12));

                Label locationLabel = new Label("Local");
                locationLabel.setPrefHeight(17);
                locationLabel.setPrefWidth(127);
                locationLabel.setTextFill(Color.WHITE);
                locationLabel.setFont(Font.font("System Bold Italic", 12));

                Label locationValue = new Label(event.getLocal());
                locationValue.setPrefHeight(20);
                locationValue.setPrefWidth(127);
                locationValue.setTextFill(Color.web("#cdc7c7"));
                locationValue.setFont(Font.font("System Italic", 12));

                Label ownerLabel = new Label("Dono do Evento");
                ownerLabel.setPrefHeight(17);
                ownerLabel.setPrefWidth(89);
                ownerLabel.setTextFill(Color.WHITE);
                ownerLabel.setFont(Font.font("System Bold Italic", 12));

                Label ownerValue = new Label(event.getOrganization());
                ownerValue.setPrefHeight(20);
                ownerValue.setPrefWidth(157);
                ownerValue.setTextFill(Color.web("#cdc7c7"));
                ownerValue.setFont(Font.font("System Italic", 12));

                Button cancelButton = new Button("Cancelar Inscrição");
                cancelButton.setStyle("-fx-background-color: transparent;");
                cancelButton.setTextFill(Color.web("#cdc7c7"));

                eventContainer.getChildren().addAll(titleLabel, descriptionLabel, startDateLabel, startDateValue, endDateLabel, endDateValue, locationLabel, locationValue, ownerLabel, ownerValue, cancelButton);
                mainContainer.getChildren().add(eventContainer);
            });
        }

        subscriptionScroll.setContent(mainContainer);
        subscriptionScroll.setFitToWidth(true);
        subscriptionScroll.setFitToHeight(true);
        System.out.println("Events displayed");
    }

}
