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
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MyEventsController implements Initializable {
    private final FacadeInterface facade = new Facade();
    private final UserInterface currentUser = UserSession.getInstance().getCurrentUser();

    @FXML
    Button configButton;

    @FXML
    StackPane myEventsPage;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private void showMyEvents() {

        List<EventInterface> events = UserSession.getInstance().getCurrentUser().getOwnerOf();

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

            Button button = new Button("Criar um evento");
            button.setOnAction(e -> {
                try {
                    moveToCreateEventPage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            button.getStyleClass().add("custom-button");
            VBox.setMargin(button, new Insets(50, 0, 0, 0));

            eventContainer.getChildren().addAll(label, button);
            mainContainer.getChildren().add(eventContainer);
            mainContainer.setAlignment(Pos.CENTER);
        } else {
            events.forEach(event -> {
                if(!event.isPrivateEvent()) {
                    VBox eventContainer = new VBox();
                    eventContainer.getStyleClass().add("container");

                    VBox.setMargin(eventContainer, new Insets(15));

                    Label title = new Label(event.getName());
                    title.getStyleClass().add("title");

                    Label description = new Label(event.getDescription());
                    description.getStyleClass().add("custom-label");


                    Label dateLabel = new Label("Data");
                    dateLabel.getStyleClass().add("caption");

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
                            throw new RuntimeException("Failed to create the subevent, " + ex);
                        }
                    });

                    Button showAllMySubEventsButton = new Button("Ver SubEventos");
                    showAllMySubEventsButton.getStyleClass().add("custom-button");
                    showAllMySubEventsButton.setOnAction(e -> {
                        try {
                            handleShowSubEvents(event);
                        } catch (IOException ex) {
                            throw new RuntimeException("Failed to show the subevents, " + ex);
                        }
                    });

                    Button editEventButton = new Button("Editar Evento");
                    editEventButton.getStyleClass().add("custom-button");
                    editEventButton.setOnAction(e -> {
                        try {
                            handleEditButton(event);
                        } catch (IOException ex) {
                            throw new RuntimeException("Failed to edit the event, " + ex);
                        }
                    });

                    Button cancelButton = new Button("Cancelar Evento");
                    cancelButton.getStyleClass().add("custom-button");
                    cancelButton.setOnAction(e -> {
                        try {
                            handleCancelButton(event);
                        } catch (IOException ex) {
                            throw new RuntimeException("Failed do cancel the event, " + ex);
                        }
                    });

                    VBox descriptionBox = new VBox(5, title, description);
                    VBox locationBox = new VBox(5, locationLabel, locationValue);
                    VBox ownerBox = new VBox(5, ownerLabel, ownerValue);


                    HBox infoBox = new HBox(50, locationBox, ownerBox);
                    HBox bottomBox1 = new HBox(50, editEventButton, cancelButton);
                    HBox bottomBox2 = new HBox(50, createSubEventButton, showAllMySubEventsButton);
                    infoBox.setAlignment(Pos.CENTER_LEFT);
                    VBox containerBox = new VBox(45,descriptionBox, infoBox, bottomBox1, bottomBox2);




                    eventContainer.getChildren().addAll(containerBox);
                    mainContainer.getChildren().add(eventContainer);
                    mainContainer.setAlignment(Pos.CENTER);
                }

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
    private void moveToCreateEventPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCriandoEvento.fxml", "Submissões", myEventsPage);
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

    private void handleCancelButton(EventInterface event) throws IOException {
        facade.deleteEvent(event, currentUser);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cancelar Evento");
        alert.setHeaderText("Evento cancelado com sucesso");
        alert.showAndWait();
        showMyEvents();

    }

}
