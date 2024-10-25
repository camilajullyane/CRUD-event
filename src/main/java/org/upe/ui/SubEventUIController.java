package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.upe.controllers.SubEventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.utils.SceneLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubEventUIController implements Initializable {

    SubEventController subEventController = new SubEventController();


    @FXML
    ScrollPane scrollPane;

    @FXML
    private StackPane subEventPage;

    @FXML
    private void moveToSettingsScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", subEventPage);

    }

    @FXML
    void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", subEventPage);
    }

    @FXML
    private void moveToHomeScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", subEventPage);
    }

    @FXML
    private void moveToScheduleScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", subEventPage);
    }

    @FXML
    private void moveToSubmissionsPage () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", subEventPage);
    }


    @FXML
    private void moveToSubscriptionButton () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", subEventPage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showSubEvents();
    }

    private void showSubEvents() {
        EventInterface parentEventInfo = SceneLoader.getEventData();

        ArrayList<SubEventInterface> subEvents = subEventController.subEventsByEvent(parentEventInfo.getId());

        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);

        subEvents.forEach(subEvent -> {
            System.out.println("SubEvent name: " + subEvent.getName());
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

            Label titleLabel = new Label(subEvent.getName());
            titleLabel.setPrefHeight(30);
            titleLabel.setPrefWidth(500);
            titleLabel.setStyle("-fx-text-fill: #2dd4bf; -fx-font-weight: bold; -fx-alignment: center;");
            titleLabel.setFont(Font.font("System Bold Italic", 16));
            titleLabel.setAlignment(Pos.CENTER);
            titleLabel.setWrapText(true);

            Label descriptionLabel = new Label(subEvent.getDescription());
            descriptionLabel.setPrefHeight(50);
            descriptionLabel.setPrefWidth(500);
            descriptionLabel.setStyle("-fx-text-fill: #cdc7c7;");
            descriptionLabel.setTextFill(Color.web("#cdc7c7"));
            descriptionLabel.setAlignment(Pos.CENTER_LEFT);
            descriptionLabel.setWrapText(true);

            Label dateLabel = new Label("Data Inicial: " + subEvent.getDate());
            dateLabel.setPrefHeight(20);
            dateLabel.setPrefWidth(500);
            dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
            dateLabel.setFont(Font.font("System Bold Italic"));
            dateLabel.setAlignment(Pos.CENTER_LEFT);

            Label locationLabel = new Label("Local: " + subEvent.getLocal());
            locationLabel.setPrefHeight(20);
            locationLabel.setPrefWidth(500);
            locationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
            locationLabel.setFont(Font.font("System Bold Italic"));
            locationLabel.setAlignment(Pos.CENTER_LEFT);

            Label ownerLabel = new Label("Dono do Evento: " + subEvent.getSpeakers());
            ownerLabel.setPrefHeight(20);
            ownerLabel.setPrefWidth(500);
            ownerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
            ownerLabel.setFont(Font.font("System Bold Italic"));
            ownerLabel.setAlignment(Pos.CENTER_LEFT);

            eventContainer.getChildren().addAll(titleLabel, descriptionLabel, dateLabel, locationLabel, ownerLabel);
            mainContainer.getChildren().add(eventContainer);
        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

}
