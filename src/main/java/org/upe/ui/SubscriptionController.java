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
    private void moveToSubmissionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", mySubscriptionPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", mySubscriptionPage);
    }

    @FXML
    private void showMyEvents() {
        System.out.println("showMyEvents called");

        List<EventInterface> events = eventController.getEventsIn(UserSession.getInstance().getCurrentUser().getCPF());
        System.out.println("Number of events: " + events.size());

        subscriptionScroll.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);

        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.setPrefWidth(350);
            eventContainer.setPrefHeight(260);
            eventContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
            eventContainer.setSpacing(10);
            eventContainer.setPadding(new Insets(10, 10, 10, 10));
            eventContainer.setAlignment(Pos.CENTER);

            Label title = new Label("Você não tem eventos inscritos");
            title.setFont(Font.font("Roboto", 16));
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
                eventContainer.setPrefHeight(260);
                eventContainer.setMinHeight(200);
                eventContainer.setMaxHeight(260);
                eventContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
                eventContainer.setSpacing(10);
                eventContainer.setPadding(new Insets(10, 10, 10, 10));
                eventContainer.setAlignment(Pos.CENTER);
                VBox.setVgrow(eventContainer, Priority.ALWAYS);

                Label titleLabel = new Label(event.getName());
                titleLabel.setPrefHeight(100);
                titleLabel.setPrefWidth(557);
                titleLabel.setStyle("-fx-text-fill: #2dd4bf;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-alignment: top-center;" +
                                    "-fx-translate-y: 70;");
                titleLabel.setFont(Font.font("System Bold Italic", 16));
                titleLabel.setAlignment(Pos.CENTER);
                titleLabel.setWrapText(true);

                Label descriptionLabel = new Label(event.getDescription());
                descriptionLabel.setPrefHeight(20);
                descriptionLabel.setPrefWidth(550);
                descriptionLabel.setStyle("-fx-translate-x: 10;" +
                                          "-fx-translate-y: 80");
                descriptionLabel.setTextFill(Color.web("#cdc7c7"));
                descriptionLabel.setAlignment(Pos.CENTER_LEFT);
                descriptionLabel.setWrapText(true);

                Label startDateLabel = new Label("Data Inicial");
                startDateLabel.setStyle("-fx-font-size: 14px;" +
                                        "-fx-text-fill: #ffffff;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-translate-x:-230;" +
                                        "-fx-translate-y: 127");
                startDateLabel.setFont(Font.font("System Bold Italic"));
                startDateLabel.setAlignment(Pos.CENTER_LEFT);

                Label startDateValue = new Label(event.getDate());
                startDateValue.setPrefHeight(20);
                startDateValue.setPrefWidth(89);
                startDateValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: -220;" +
                        "-fx-translate-y: 120");
                startDateValue.setTextFill(Color.web("#cdc7c7"));
                startDateValue.setAlignment(Pos.CENTER_LEFT);

                Label endDateLabel = new Label("Data Final");
                endDateLabel.setPrefHeight(17);
                endDateLabel.setPrefWidth(89);
                endDateLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: -100;" +
                        "-fx-translate-y: 70");
                endDateLabel.setFont(Font.font("System Bold Italic"));
                endDateLabel.setAlignment(Pos.CENTER_LEFT);

                Label endDateValue = new Label(event.getDate());
                endDateValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: -90;" +
                        "-fx-translate-y:63");
                endDateValue.setTextFill(Color.web("#cdc7c7"));
                endDateValue.setFont(Font.font("System Italic"));
                endDateValue.setAlignment(Pos.CENTER_LEFT);

                Label locationLabel = new Label("Local");
                locationLabel.setPrefHeight(17);
                locationLabel.setPrefWidth(127);
                locationLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: 60;" +
                        "-fx-translate-y: 13");
                locationLabel.setTextFill(Color.WHITE);
                locationLabel.setFont(Font.font("System Bold Italic"));
                locationLabel.setAlignment(Pos.CENTER_LEFT);

                Label locationValue = new Label(event.getLocal());
                locationValue.setPrefHeight(20);
                locationValue.setPrefWidth(127);
                locationValue.setStyle("-fx-font-size: 12px;" +
                                        "-fx-text-fill: #cdc7c7;" +
                                        "-fx-translate-x: 60;" +
                                        "-fx-translate-y: 7");
                locationValue.setTextFill(Color.web("#cdc7c7"));
                locationValue.setFont(Font.font("System Italic"));
                locationValue.setAlignment(Pos.CENTER_LEFT);

                Label ownerLabel = new Label("Dono do Evento");
                ownerLabel.setPrefHeight(17);
                ownerLabel.setPrefWidth(200);
                ownerLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: 170;" +
                        "-fx-translate-y: -43");
                ownerLabel.setTextFill(Color.WHITE);
                ownerLabel.setFont(Font.font("System Bold Italic"));
                ownerLabel.setAlignment(Pos.CENTER_LEFT);

                Label ownerValue = new Label(event.getOrganization());
                ownerValue.setPrefHeight(20);
                ownerValue.setPrefWidth(157);
                ownerValue.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: 150;" +
                        "-fx-translate-y: -50");
                ownerValue.setTextFill(Color.web("#cdc7c7"));
                ownerValue.setFont(Font.font("System Italic"));
                ownerValue.setAlignment(Pos.CENTER_LEFT);

                Button cancelButton = new Button("Cancelar Inscrição");
                cancelButton.setStyle("-fx-background-color: transparent;  " +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: #bbbbbb;" +
                        "-fx-underline: true;" +
                        "-fx-translate-x: 220;" +
                        "-fx-translate-y: -230;");
                cancelButton.setTextFill(Color.web("#cdc7c7"));
                cancelButton.setAlignment(Pos.CENTER_LEFT);

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
