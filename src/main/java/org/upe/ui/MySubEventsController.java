package org.upe.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.upe.controllers.SubEventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;
import java.util.ArrayList;

public class MySubEventsController {
    SubEventController subEventController = new SubEventController();
    private static final String FONT_STYLE_BOLD_ITALIC = "System Bold Italic";
    private static final String FONT_SYSTEM_ITALIC = "System Italic";
    private static final Paint color = Color.web("#cdc7c7");

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane mySubEventsPage;

    public void initialize() {
        showMySubEvents();
    }

    private void showMySubEvents() {
        EventInterface currentEvent = SceneLoader.getEventData();
        ArrayList<SubEventInterface> subEvents = subEventController. getAllSubEventsByEvent(currentEvent.getId());

        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);

        if (subEvents.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.setPrefWidth(350);
            eventContainer.setPrefHeight(260);
            eventContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
            eventContainer.setSpacing(10);
            eventContainer.setPadding(new Insets(10, 10, 10, 10));
            eventContainer.setAlignment(Pos.CENTER);

            Label title = new Label("Você não tem SubEventos criados");
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
            subEvents.forEach(subEvent -> {
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
                titleLabel.setPrefHeight(100);
                titleLabel.setPrefWidth(557);
                titleLabel.setStyle("-fx-text-fill: #2dd4bf;" +
                        "-fx-font-weight: bold;" +
                        "-fx-alignment: top-center;" +
                        "-fx-translate-y: 70;");
                titleLabel.setFont(Font.font(FONT_STYLE_BOLD_ITALIC, 16));
                titleLabel.setAlignment(Pos.CENTER);
                titleLabel.setWrapText(true);

                Label descriptionLabel = new Label(subEvent.getDescription());
                descriptionLabel.setPrefHeight(20);
                descriptionLabel.setPrefWidth(550);
                descriptionLabel.setStyle("-fx-translate-x: 10;" +
                        "-fx-translate-y: 80");
                descriptionLabel.setTextFill(color);
                descriptionLabel.setAlignment(Pos.CENTER_LEFT);
                descriptionLabel.setWrapText(true);

                Label startDateLabel = new Label("Data Inicial");
                startDateLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x:-230;" +
                        "-fx-translate-y: 127");
                startDateLabel.setFont(Font.font(FONT_STYLE_BOLD_ITALIC));
                startDateLabel.setAlignment(Pos.CENTER_LEFT);

                Label startDateValue = new Label(subEvent.getDate());
                startDateValue.setPrefHeight(20);
                startDateValue.setPrefWidth(89);
                startDateValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: -220;" +
                        "-fx-translate-y: 120");
                startDateValue.setTextFill(color);
                startDateValue.setAlignment(Pos.CENTER_LEFT);

                Label locationLabel = new Label("Local");
                locationLabel.setPrefHeight(17);
                locationLabel.setPrefWidth(127);
                locationLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: 60;" +
                        "-fx-translate-y: 13");
                locationLabel.setTextFill(javafx.scene.paint.Color.WHITE);
                locationLabel.setFont(Font.font(FONT_STYLE_BOLD_ITALIC));
                locationLabel.setAlignment(Pos.CENTER_LEFT);

                Label locationValue = new Label(subEvent.getLocal());
                locationValue.setPrefHeight(20);
                locationValue.setPrefWidth(127);
                locationValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: 60;" +
                        "-fx-translate-y: 7");
                locationValue.setTextFill(color);
                locationValue.setFont(Font.font(FONT_SYSTEM_ITALIC));
                locationValue.setAlignment(Pos.CENTER_LEFT);

                Label speakersLabel = new Label("Palestrantes do Evento");
                speakersLabel.setPrefHeight(17);
                speakersLabel.setPrefWidth(200);
                speakersLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: 170;" +
                        "-fx-translate-y: -43");
                speakersLabel.setTextFill(Color.WHITE);
                speakersLabel.setFont(Font.font(FONT_STYLE_BOLD_ITALIC));
                speakersLabel.setAlignment(Pos.CENTER_LEFT);

                Label speakersValue = new Label(subEvent.getSpeakers());
                speakersValue.setPrefHeight(20);
                speakersValue.setPrefWidth(157);
                speakersValue.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: 150;" +
                        "-fx-translate-y: -50");
                speakersValue.setTextFill(color);
                speakersValue.setFont(Font.font(FONT_SYSTEM_ITALIC));
                speakersValue.setAlignment(Pos.CENTER_LEFT);

                eventContainer.getChildren().addAll(titleLabel, descriptionLabel, startDateLabel, startDateValue,
                        locationLabel, locationValue,
                        speakersLabel, speakersValue);

                mainContainer.getChildren().add(eventContainer);
            });
        }

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }


    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", mySubEventsPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", mySubEventsPage);
    }

    @FXML
    private void moveToMyEventsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/myEvents.fxml", "Meus Eventos", mySubEventsPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", mySubEventsPage);
    }

    @FXML
    private void moveToSubscriptionButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", mySubEventsPage);
    }

    @FXML
    private void moveToSubmissionsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", mySubEventsPage);
    }

    @FXML
    private void backToCertificate() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", mySubEventsPage);
    }

    @FXML
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", mySubEventsPage);
    }

}
