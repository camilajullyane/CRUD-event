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

public class SubscriptionController implements Initializable {
    FacadeInterface facade = new Facade();

    @FXML
    Button settingsButton;

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
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", mySubscriptionPage);
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
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", mySubscriptionPage);
    }

    @FXML
    private void showMyEvents() {

        List<EventInterface> events = UserSession.getInstance().getCurrentUser().getAttendeeOn();

        subscriptionScroll.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());
        subscriptionScroll.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();

        if (events.isEmpty()) {
            VBox eventContainer = new VBox();
            eventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(eventContainer, new Insets(30));

            Label label = new Label("Você não se inscreveu em nenhum evento");
            label.getStyleClass().add("custom-label");

            Button button = new Button("Ver eventos disponíveis");
            button.getStyleClass().add("custom-button");
            VBox.setMargin(button, new Insets(50, 0, 0, 0));

            eventContainer.getChildren().addAll(label, button);
            mainContainer.getChildren().add(eventContainer);
            mainContainer.setAlignment(Pos.CENTER);
        } else {
            events.forEach(event -> {
                if (!event.isPrivateEvent()) {
                    VBox eventContainer = new VBox();
                    eventContainer.getStyleClass().add("container");

                    VBox.setMargin(eventContainer, new Insets(15));

                    Label title = new Label(event.getName());
                    title.getStyleClass().add("title");

                    Label description = new Label(event.getDescription());
                    description.getStyleClass().add("custom-label");

                    Label date = new Label("Data");
                    date.getStyleClass().add("caption");

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
                    cancelButton.setOnAction(e -> cancelSubscription(event));

                    VBox descriptionBox = new VBox(5, title, description);
                    VBox locationBox = new VBox(5, location, locationValue);
                    VBox ownerBox = new VBox(5, owner, ownerValue);


                    HBox infoBox = new HBox(50, locationBox, ownerBox);
                    HBox headerBox = new HBox(20, descriptionBox, cancelButton);
                    infoBox.setAlignment(Pos.CENTER_LEFT);

                    VBox containerBox = new VBox(45, headerBox, infoBox);

                    eventContainer.getChildren().addAll(containerBox);
                    mainContainer.getChildren().add(eventContainer);
                    mainContainer.setAlignment(Pos.CENTER);
                }
            });
        }

        subscriptionScroll.setContent(mainContainer);
        subscriptionScroll.setFitToWidth(true);
        subscriptionScroll.setFitToHeight(true);
    }

    private void cancelSubscription(EventInterface event) {
        UserSession userSession = UserSession.getInstance();

        facade.deleteAttendeeOnList(userSession.getCurrentUser(), event);
        userSession.setCurrentUser(facade.getUserByCPF(userSession.getCurrentUser().getCpf()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cancelar Inscrição");
        alert.setHeaderText(null);
        alert.setContentText("Inscrição cancelada com sucesso!");
        alert.showAndWait();

        showMyEvents();

    }
}
