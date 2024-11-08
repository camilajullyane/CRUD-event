package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
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

public class MyEventsController implements Initializable {
    private final FacadeInterface facade = new Facade();

    @FXML
    Button configButton;

    @FXML
    StackPane myEventsPage;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private void showMyEvents() {

        List<EventInterface> events = facade.getAllEventsByUser(UserSession.getInstance().getCurrentUser().getCpf());;

        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());
        scrollPane.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();

        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(eventContainer, new Insets(30));

            Label label = new Label("Você não tem eventos criados");
            label.getStyleClass().add("custom-label");

            eventContainer.getChildren().add(label);
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


                Label dateLabel = new Label("Data");
                dateLabel.getStyleClass().add("caption");


                Label dateValue = new Label(event.getDate());
                dateValue.getStyleClass().add("subcaption");


                Label locationLabel = new Label("Local");
                locationLabel.getStyleClass().add("caption");


                Label locationValue = new Label(event.getLocal());
                locationValue.getStyleClass().add("subcaption");


                Label ownerLabel = new Label("Dono do Evento");
                ownerLabel.getStyleClass().add("caption");

                Label ownerValue = new Label(event.getOrganization());
                ownerValue.getStyleClass().add("subcaption");


                Button createSubEventButton = new Button("Criar SubEvento");
                createSubEventButton.getStyleClass().add("custom-button");
                createSubEventButton.setOnAction(e -> {
                    try {
                        handleCreateSubEvent(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Button showAllMySubEventsButton = new Button("Ver SubEventos");
                showAllMySubEventsButton.getStyleClass().add("custom-button");
                showAllMySubEventsButton.setOnAction(e -> {
                    try {
                        handleShowSubEvents(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Button editEventButton = new Button("Editar Evento");
                editEventButton.getStyleClass().add("custom-button");
                editEventButton.setOnAction(e -> {
                    try {
                        handleEditButton(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                // Botão de apagar evento
//                Button cancelButton = new Button("Apagar evento");
//                cancelButton.setStyle("-fx-background-color: transparent;  " +
//                        "-fx-font-size: 12px;" +
//                        "-fx-text-fill: #bbbbbb;" +
//                        "-fx-underline: true;" +
//                        "-fx-translate-x: 220;" +
//                        "-fx-translate-y: -230;");
//                cancelButton.setTextFill(color);
//                cancelButton.setAlignment(Pos.CENTER_LEFT);

                VBox descriptionBox = new VBox(5, title, description);
                VBox dateBox = new VBox(5, dateLabel, dateValue);
                VBox locationBox = new VBox(5, locationLabel, locationValue);
                VBox ownerBox = new VBox(5, ownerLabel, ownerValue);


                HBox infoBox = new HBox(50, dateBox, locationBox, ownerBox);
                HBox bottomBox = new HBox(50, createSubEventButton, showAllMySubEventsButton, editEventButton);
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

    private void handleEditButton(EventInterface event) throws IOException {
        SceneLoader.setEventData(event);
        SceneLoader.loadScene("/org/upe/ui/telaEditarEvento.fxml", "Editar Evento", myEventsPage);
    }

}
