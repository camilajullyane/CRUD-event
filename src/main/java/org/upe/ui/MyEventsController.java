package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyEventsController implements Initializable {

    EventController eventController = new EventController();
    private static final String FONT_STYLE_BOLD_ITALIC = "System Bold Italic";
    private static final String FONT_SYSTEM_ITALIC = "System Italic";
    private static final Paint color = Color.web("#cdc7c7");

    @FXML
    Button configButton;

    @FXML
    StackPane myEventsPage;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private void showMyEvents() {

        List<EventInterface> events = eventController.getAllEventsByUser(UserSession.getInstance().getCurrentUser().getCPF());;

        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
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
            eventContainer.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            eventContainer.setAlignment(Pos.CENTER);

            javafx.scene.control.Label title = new javafx.scene.control.Label("Você não tem eventos criados");
            title.setFont(javafx.scene.text.Font.font("Roboto", 16));
            title.setTextFill(javafx.scene.paint.Color.WHITE);
            VBox.setVgrow(title, Priority.ALWAYS);
            title.setMaxWidth(Double.MAX_VALUE);
            title.setAlignment(Pos.CENTER);

            eventContainer.getChildren().add(title);

            mainContainer.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
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

                javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(event.getName());
                titleLabel.setPrefHeight(100);
                titleLabel.setPrefWidth(557);
                titleLabel.setStyle("-fx-text-fill: #2dd4bf;" +
                        "-fx-font-weight: bold;" +
                        "-fx-alignment: top-center;" +
                        "-fx-translate-y: 70;");
                titleLabel.setFont(javafx.scene.text.Font.font(FONT_STYLE_BOLD_ITALIC, 16));
                titleLabel.setAlignment(Pos.CENTER);
                titleLabel.setWrapText(true);

                javafx.scene.control.Label descriptionLabel = new javafx.scene.control.Label(event.getDescription());
                descriptionLabel.setPrefHeight(20);
                descriptionLabel.setPrefWidth(550);
                descriptionLabel.setStyle("-fx-translate-x: 10;" +
                        "-fx-translate-y: 80");
                descriptionLabel.setTextFill(color);
                descriptionLabel.setAlignment(Pos.CENTER_LEFT);
                descriptionLabel.setWrapText(true);

                javafx.scene.control.Label startDateLabel = new javafx.scene.control.Label("Data Inicial");
                startDateLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x:-230;" +
                        "-fx-translate-y: 127");
                startDateLabel.setFont(javafx.scene.text.Font.font(FONT_STYLE_BOLD_ITALIC));
                startDateLabel.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Label startDateValue = new javafx.scene.control.Label(event.getDate());
                startDateValue.setPrefHeight(20);
                startDateValue.setPrefWidth(89);
                startDateValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: -220;" +
                        "-fx-translate-y: 120");
                startDateValue.setTextFill(color);
                startDateValue.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Label endDateLabel = new javafx.scene.control.Label("Data Final");
                endDateLabel.setPrefHeight(17);
                endDateLabel.setPrefWidth(89);
                endDateLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: -100;" +
                        "-fx-translate-y: 70");
                endDateLabel.setFont(javafx.scene.text.Font.font(FONT_STYLE_BOLD_ITALIC));
                endDateLabel.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Label endDateValue = new javafx.scene.control.Label(event.getDate());
                endDateValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: -90;" +
                        "-fx-translate-y:63");
                endDateValue.setTextFill(color);
                endDateValue.setFont(javafx.scene.text.Font.font(FONT_SYSTEM_ITALIC));
                endDateValue.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Label locationLabel = new javafx.scene.control.Label("Local");
                locationLabel.setPrefHeight(17);
                locationLabel.setPrefWidth(127);
                locationLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: 60;" +
                        "-fx-translate-y: 13");
                locationLabel.setTextFill(javafx.scene.paint.Color.WHITE);
                locationLabel.setFont(javafx.scene.text.Font.font(FONT_STYLE_BOLD_ITALIC));
                locationLabel.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Label locationValue = new javafx.scene.control.Label(event.getLocal());
                locationValue.setPrefHeight(20);
                locationValue.setPrefWidth(127);
                locationValue.setStyle("-fx-font-size: 12px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: 60;" +
                        "-fx-translate-y: 7");
                locationValue.setTextFill(color);
                locationValue.setFont(javafx.scene.text.Font.font(FONT_SYSTEM_ITALIC));
                locationValue.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Label ownerLabel = new javafx.scene.control.Label("Dono do Evento");
                ownerLabel.setPrefHeight(17);
                ownerLabel.setPrefWidth(200);
                ownerLabel.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-translate-x: 170;" +
                        "-fx-translate-y: -43");
                ownerLabel.setTextFill(Color.WHITE);
                ownerLabel.setFont(javafx.scene.text.Font.font(FONT_STYLE_BOLD_ITALIC));
                ownerLabel.setAlignment(Pos.CENTER_LEFT);

                Button createSubEventButton = new Button("Criar SubEvento");
                createSubEventButton.setStyle("-fx-background-radius: 25;" +
                        "-fx-background-color: #2DD4BF;" +
                        "-fx-text-fill: WHITE;"
                        + "-fx-translate-x: 100;"
                        + "-fx-translate-y: -74");
                createSubEventButton.setFont(Font.font("Arial", 14));
                createSubEventButton.setOnAction(e -> {
                    try {
                        handleCreateSubEvent(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Button showAllMySubEventsButton = new Button("Ver SubEventos");
                showAllMySubEventsButton.setStyle("-fx-background-radius: 25;" +
                        "-fx-background-color: #2DD4BF;" +
                        "-fx-text-fill: WHITE;"
                        + "-fx-translate-x: -100;"
                        + "-fx-translate-y: -110");
                showAllMySubEventsButton.setFont(Font.font("Arial", 14));
                showAllMySubEventsButton.setOnAction(e -> {
                    try {
                        handleShowSubEvents(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });


                javafx.scene.control.Label ownerValue = new javafx.scene.control.Label(event.getOrganization());
                ownerValue.setPrefHeight(20);
                ownerValue.setPrefWidth(157);
                ownerValue.setStyle("-fx-font-size: 14px;" +
                        "-fx-text-fill: #cdc7c7;" +
                        "-fx-translate-x: 150;" +
                        "-fx-translate-y: -50");
                ownerValue.setTextFill(color);
                ownerValue.setFont(Font.font(FONT_SYSTEM_ITALIC));
                ownerValue.setAlignment(Pos.CENTER_LEFT);

                javafx.scene.control.Button cancelButton = new javafx.scene.control.Button("Cancelar Inscrição");
                cancelButton.setStyle("-fx-background-color: transparent;  " +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: #bbbbbb;" +
                        "-fx-underline: true;" +
                        "-fx-translate-x: 220;" +
                        "-fx-translate-y: -230;");
                cancelButton.setTextFill(color);
                cancelButton.setAlignment(Pos.CENTER_LEFT);

                eventContainer.getChildren().addAll(titleLabel, descriptionLabel, startDateLabel, startDateValue,
                        endDateLabel, endDateValue, locationLabel, locationValue,
                        ownerLabel, ownerValue, cancelButton, createSubEventButton, showAllMySubEventsButton);

                mainContainer.getChildren().add(eventContainer);
            });
        }

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMyEvents();
    }

    @FXML
    private void moveToSettingsScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", myEventsPage);

    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", myEventsPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", myEventsPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", myEventsPage);
    }

    @FXML
    private void moveToSubscriptionButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", myEventsPage);
    }

    @FXML
    private void moveToSubmissionsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", myEventsPage);
    }

    @FXML
    private void backToCertificate() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Submissões", myEventsPage);
    }


    private void handleCreateSubEvent(EventInterface event) throws IOException {
        SceneLoader.setEventData(event);
        SceneLoader.loadScene("/org/upe/ui/telaCriandoSubEvento.fxml", "Criar SubEvento", myEventsPage);
    }

    private void handleShowSubEvents(EventInterface event) throws IOException {
        SceneLoader.setEventData(event);
        SceneLoader.loadScene("/org/upe/ui/mySubEvents.fxml", "Meus SubEventos", myEventsPage);
    }

}
