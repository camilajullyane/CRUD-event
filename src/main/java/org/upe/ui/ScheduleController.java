package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.upe.controllers.EventController;
import org.upe.controllers.UserController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {
    UserController userController = new UserController();
    EventController eventController = new EventController();

    @FXML
    Button settingsButton;

    @FXML
    StackPane schedulePage;

    @FXML
    ScrollPane scrollPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showEvents();
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", schedulePage);
    }

    @FXML
    private void moveToRegisterInScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", schedulePage);
    }

    @FXML
    private void moveToSubmissionsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", schedulePage);
    }

    @FXML
    private void moveToCertificatesScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", schedulePage);
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Login", schedulePage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", schedulePage);
    }

    private void showEvents() {

        List<EventInterface> events = eventController.getAllEvents();

        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);


        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.setPrefWidth(350);
            eventContainer.setPrefHeight(260);
            eventContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
            eventContainer.setSpacing(10);
            eventContainer.setPadding(new Insets(10, 10, 10, 10));
            eventContainer.setAlignment(Pos.CENTER);


            Label title = new Label("Nenhum evento disponível no momento");
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
                eventContainer.setPrefHeight(400);
                eventContainer.setMinHeight(210);
                eventContainer.setMaxHeight(400);
                eventContainer.setStyle("-fx-background-color: rgba(217, 217, 217, 0.1); -fx-background-radius: 25px;");
                eventContainer.setSpacing(20);
                eventContainer.setPadding(new Insets(20, 20, 20, 20));

                Label title = new Label(event.getName());
                title.setStyle("-fx-text-fill: #2dd4bf;" + "-fx-font-weight: bold;");
                title.setFont(Font.font("Arial", 18));
                title.setTextFill(Color.WHITE);
                title.setAlignment(Pos.TOP_CENTER);
                VBox.setVgrow(title, Priority.ALWAYS);
                title.setMaxWidth(Double.MAX_VALUE);
                title.setAlignment(Pos.CENTER);

                Label description = new Label(event.getDescription());
                description.setFont(Font.font("Arial", 14));
                description.setTextFill(Color.WHITE);
                description.setWrapText(true);

                Label startDateLabel = new Label("Data");
                startDateLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: #ffffff;" + "-fx-font-weight: bold;" + "-fx-translate-x: 0;" + "-fx-translate-y: 20");
                startDateLabel.setFont(Font.font("System Bold Italic"));
                startDateLabel.setTextFill(Color.WHITE);

                Label locationLabel = new Label("Local");
                locationLabel.setPrefHeight(17);
                locationLabel.setPrefWidth(127);
                locationLabel.setStyle("-fx-font-size: 14px;" +"-fx-text-fill: #ffffff;" + "-fx-font-weight: bold;" + "-fx-translate-x: 130;" + "-fx-translate-y: -53");
                locationLabel.setTextFill(Color.WHITE);
                locationLabel.setFont(Font.font("System Bold Italic"));
                locationLabel.setAlignment(Pos.CENTER_LEFT);

                Label dateLabel = new Label(event.getDate());
                dateLabel.setFont(Font.font("Arial", 14));
                dateLabel.setStyle("-fx-font-size: 12px;" +"-fx-text-fill: #cdc7c7;" +"-fx-translate-x: 0;" +"-fx-translate-y:0");
                dateLabel.setTextFill(Color.WHITE);

                Button signUpButton = new Button("Realizar Inscrição");
                signUpButton.setStyle("-fx-background-radius: 25;" + "-fx-background-color: #2DD4BF;" + "-fx-text-fill: WHITE;"  + "-fx-translate-x: 100;" + "-fx-translate-y: -74");
                signUpButton.setFont(Font.font("Arial", 14));
                signUpButton.setOnAction(e -> signUpEvent(event));

                Button seeSubEvents = new Button("Ver SubEventos");
                seeSubEvents.setStyle("-fx-background-radius: 25;" + "-fx-background-color: #2DD4BF;" + "-fx-text-fill: WHITE;"  + "-fx-translate-x: 300;" + "-fx-translate-y: -120");
                seeSubEvents.setFont(Font.font("Arial", 14));
                seeSubEvents.setOnAction(e -> {
                    try {
                        showSubEvents(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Label locationValue = new Label(event.getLocal());
                locationValue.setStyle("-fx-font-size: 12px;" + "-fx-text-fill: #cdc7c7;" + "-fx-translate-x: 130;" + "-fx-translate-y: -75");
                locationValue.setFont(Font.font("Arial", 14));
                locationValue.setTextFill(Color.WHITE);

                eventContainer.getChildren().addAll(title, description, startDateLabel, dateLabel, locationLabel, locationValue, signUpButton, seeSubEvents);
                mainContainer.getChildren().add(eventContainer);
            });
        }

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    private void signUpEvent(EventInterface event) {
        UserSession userSession = UserSession.getInstance();

        boolean isAlreadySubscribed = eventController.addAttendeeOnList(userSession.getCurrentUser(), event);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(!isAlreadySubscribed) {
            alert.setTitle("Inscrição");
            alert.setHeaderText(null);
            alert.setContentText("Você já está inscrito neste evento!");
            alert.showAndWait();
        } else {
            alert.setTitle("Inscrição");
            alert.setHeaderText(null);
            alert.setContentText("Inscrição realizada com sucesso!");
            alert.showAndWait();
            UserSession.getInstance().setCurrentUser(userController.getUserByCPF(UserSession.getInstance().getCurrentUser().getCPF()));
        }
    }

    @FXML
    private void showSubEvents(EventInterface event) throws IOException {
        SceneLoader.setEventData(event);
        SceneLoader.loadScene("/org/upe/ui/telaSubEvento.fxml", "SubEventos", schedulePage);
    }

    @FXML
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Login", schedulePage);
    }
}