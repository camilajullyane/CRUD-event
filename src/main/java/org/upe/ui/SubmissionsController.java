package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SubmissionsController implements Initializable {

    EventController eventController = new EventController();

    @FXML
    public Button settingsButton;

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private Button certificateButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Text place;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private StackPane submissionsPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showEvents();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", submissionsPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", submissionsPage);
    }

    @FXML
    private void moveToSubscriptionScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", submissionsPage);
    }

    @FXML
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", submissionsPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Inicio", submissionsPage);
    }

    @FXML
    private void moveToCertificateScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", submissionsPage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", submissionsPage);
    }

    @FXML
    private void showEvents() {

        List<EventInterface> events = eventController.getAllEvents();

        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());
        scrollPane.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();

        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(eventContainer, new Insets(30));

            Label label = new Label("Você não submeteu nenhum artigo");
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

                Button cancelButton = new Button("Cancelar Inscrição");
                cancelButton.getStyleClass().add("cancel-button");

                VBox descriptionBox = new VBox(5, title, description);
                VBox dateBox = new VBox(5, date, dateValue);
                VBox locationBox = new VBox(5, location, locationValue);
                VBox ownerBox = new VBox(5, owner, ownerValue);


                HBox infoBox = new HBox(50, dateBox, locationBox, ownerBox);
                HBox headerBox = new HBox(20, descriptionBox, cancelButton);
                infoBox.setAlignment(Pos.CENTER_LEFT);
                VBox containerBox = new VBox(45, headerBox, infoBox);

                eventContainer.getChildren().addAll(containerBox);
                mainContainer.getChildren().add(eventContainer);
                mainContainer.setAlignment(Pos.CENTER);
            });
        }

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
    
}
