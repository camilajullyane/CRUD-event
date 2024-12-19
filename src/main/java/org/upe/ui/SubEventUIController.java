package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.upe.controllers.SubEventController;
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SubEventUIController implements Initializable {
    private final FacadeInterface facade = new Facade();
    private final UserInterface currentUser = UserSession.getCurrentUser();

    @FXML
    ScrollPane scrollPane;

    @FXML
    private StackPane subEventPage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showSubEvents();
    }

    private void showSubEvents() {
        EventInterface parentEventInfo = SceneLoader.getEventData();

        List<SubEventInterface> subEvents = parentEventInfo.getSubEvents();

        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());
        scrollPane.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();

        subEvents.forEach(subEvent -> {
            if (subEvent.isPrivateSubEvent()) {
                return;
            }
            VBox eventContainer = new VBox();
            eventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(eventContainer, new Insets(30));


            Label title = new Label(subEvent.getName());
            title.getStyleClass().add("title");

            Label description = new Label(subEvent.getDescription());
            description.getStyleClass().add("custom-label");

            Label beginDateLabel = new Label("Data inicial");
            beginDateLabel.getStyleClass().add("caption");

            Label beginDateValue = new Label(subEvent.getBeginDate().toString());
            beginDateValue.getStyleClass().add("subcaption");

            Label endDateLabel = new Label("Data Final");
            endDateLabel.getStyleClass().add("caption");

            Label endDateValue = new Label(subEvent.getEndDate().toString());
            endDateValue.getStyleClass().add("subcaption");

            Label location = new Label("Local");
            location.getStyleClass().add("caption");


            Button seeSessionsButton = new Button("Ver Sessões");
            seeSessionsButton.getStyleClass().add("custom-button");
            seeSessionsButton.setOnAction(e -> {
                try {
                    handleSeeSession(subEvent);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button subscribeButton = new Button("Inscrever-se");
            subscribeButton.getStyleClass().add("custom-button");
            subscribeButton.setOnAction(e -> {
                try {
                    handleSubscriptionButton(subEvent);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            VBox descriptionBox = new VBox(5, title, description);
            VBox beginDateBox = new VBox(5, beginDateLabel, beginDateValue);
            VBox endDateBox = new VBox(5, endDateLabel, endDateValue);
            HBox infoBox = new HBox(50, beginDateBox, endDateBox);
            HBox bottomBox = new HBox(50, seeSessionsButton, subscribeButton);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            VBox containerBox = new VBox(45,descriptionBox, infoBox, bottomBox);

            eventContainer.getChildren().addAll(containerBox);
            mainContainer.getChildren().add(eventContainer);
            mainContainer.setAlignment(Pos.CENTER);

        });
        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

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

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", subEventPage);
    }

    @FXML
    private void handleSeeSession(SubEventInterface subEvent) throws IOException {
        SceneLoader.setSubEventData(subEvent);
        SceneLoader.loadScene("/org/upe/ui/telaSecao.fxml", "Sessões", subEventPage);
    }

    @FXML
    private void handleSubscriptionButton(SubEventInterface subEvent) throws IOException {
        boolean isSubscribed = facade.addAttendeeSubEventOnList(currentUser, subEvent);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(!isSubscribed) {
            alert.setTitle("Inscrição");
            alert.setHeaderText(null);
            alert.setContentText("Você já está inscrito neste subEvento ou é dono dele!");
            alert.showAndWait();
        } else {
            alert.setTitle("Inscrição");
            alert.setHeaderText(null);
            alert.setContentText("Inscrição realizada com sucesso!");
            alert.showAndWait();
            UserSession.getInstance().setCurrentUser(facade.getUserByCPF(UserSession.getInstance().getCurrentUser().getCpf()));
        }

        showSubEvents();
    }

}
