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
import org.upe.controllers.EventController;
import org.upe.controllers.UserController;
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {
    private final FacadeInterface facade = new Facade();

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

        List<EventInterface> events = facade.getAllEvents();

        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());
        scrollPane.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();

        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(eventContainer, new Insets(30));

            Label label = new Label("Não há eventos disponíveis no momento");
            label.getStyleClass().add("custom-label");

            Button button = new Button("Ver eventos disponíveis");
            button.getStyleClass().add("custom-button");
            VBox.setMargin(button, new Insets(50, 0, 0, 0));

            eventContainer.getChildren().addAll(label, button);
            mainContainer.getChildren().add(eventContainer);
            mainContainer.setAlignment(Pos.CENTER);
        } else {
            events.forEach(event -> {
                VBox eventContainer = new VBox();
                eventContainer.getStyleClass().add("container");

                VBox.setMargin(eventContainer, new Insets(15));

                Label title = new Label(event.getName());
                title.getStyleClass().add("title");

                Label description = new Label(event.getDescription());
                description.getStyleClass().add("custom-label");

                Label date = new Label("Data");
                date.getStyleClass().add("caption");

                Label dateValue = new Label(event.getDate());
                dateValue.getStyleClass().add("subcaption");

                Label location = new Label("Local");
                location.getStyleClass().add("caption");

                Label locationValue = new Label(event.getLocal());
                locationValue.getStyleClass().add("subcaption");

                Label owner = new Label("Dono do Evento");
                owner.getStyleClass().add("caption");

                Label ownerValue = new Label(event.getOrganization());
                ownerValue.getStyleClass().add("subcaption");

                Button signUpButton = new Button("Realizar Inscrição");
                signUpButton.getStyleClass().add("custom-button");
                signUpButton.setOnAction(e -> signUpEvent(event));

                Button seeSubEvents = new Button("Ver SubEventos");
                seeSubEvents.getStyleClass().add("custom-button");
                seeSubEvents.setOnAction(e -> {
                    try {
                        showSubEvents(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                VBox descriptionBox = new VBox(5, title, description);
                VBox dateBox = new VBox(5, date, dateValue);
                VBox locationBox = new VBox(5, location, locationValue);
                VBox ownerBox = new VBox(5, owner, ownerValue);


                HBox infoBox = new HBox(50, dateBox, locationBox, ownerBox);
                HBox bottomBox = new HBox(50, signUpButton, seeSubEvents);
                infoBox.setAlignment(Pos.CENTER_LEFT);
                bottomBox.setAlignment(Pos.CENTER);
                VBox containerBox = new VBox(45,descriptionBox, infoBox, bottomBox);


                eventContainer.getChildren().addAll(containerBox);
                mainContainer.getChildren().add(eventContainer);
                mainContainer.setAlignment(Pos.CENTER);
            });
        }

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }


    private void signUpEvent(EventInterface event) {
        UserSession userSession = UserSession.getInstance();

        boolean isAlreadySubscribed = facade.addAttendeeOnList(userSession.getCurrentUser(), event);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(!isAlreadySubscribed) {
            alert.setTitle("Inscrição");
            alert.setHeaderText(null);
            alert.setContentText("Você já está inscrito neste evento ou é dono do evento!");
            alert.showAndWait();
        } else {
            alert.setTitle("Inscrição");
            alert.setHeaderText(null);
            alert.setContentText("Inscrição realizada com sucesso!");
            alert.showAndWait();
            UserSession.getInstance().setCurrentUser(facade.getUserByCPF(UserSession.getInstance().getCurrentUser().getCPF()));
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