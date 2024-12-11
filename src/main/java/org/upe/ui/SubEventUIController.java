package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.utils.SceneLoader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SubEventUIController implements Initializable {
    private final FacadeInterface facade = new Facade();

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
            VBox eventContainer = new VBox();
            eventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(eventContainer, new Insets(30));


            Label title = new Label(subEvent.getName());
            title.getStyleClass().add("title");

            Label description = new Label(subEvent.getDescription());
            description.getStyleClass().add("custom-label");


            Label date = new Label("Data");
            date.getStyleClass().add("caption");

            Label dateValue = new Label(subEvent.getBeginDate().toString());
            dateValue.getStyleClass().add("subcaption");

            Label location = new Label("Local");
            location.getStyleClass().add("caption");

            Label owner = new Label("Palestrantes do evento");
            owner.getStyleClass().add("caption");


            VBox descriptionBox = new VBox(5, title, description);
            VBox dateBox = new VBox(5, date, dateValue);


            HBox infoBox = new HBox(50, dateBox);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            VBox containerBox = new VBox(45,descriptionBox, infoBox);

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

}
