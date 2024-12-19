package org.upe.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MySubEventsController {
    private final FacadeInterface facade = new Facade();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane mySubEventsPage;

    public void initialize() {
        showMySubEvents();
    }

    private void showMySubEvents() {
        EventInterface currentEvent = SceneLoader.getEventData();

        List<SubEventInterface> subEvents = currentEvent.getSubEvents();

        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());

        scrollPane.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();

        if (subEvents.isEmpty()) {
            VBox subEventContainer = new VBox();
            subEventContainer.getStyleClass().add("custom-vbox");

            VBox.setMargin(subEventContainer, new Insets(30));


            Label label = new Label("Você não tem SubEventos criados");
            label.getStyleClass().add("custom-label");

            subEventContainer.getChildren().add(label);

            mainContainer.setPadding(new Insets(20, 20, 20, 20));
            mainContainer.getChildren().add(subEventContainer);
            mainContainer.setAlignment(Pos.CENTER);
        } else {
            subEvents.forEach(subEvent -> {
                VBox subEventContainer = new VBox();
                subEventContainer.getStyleClass().add("container");

                VBox.setMargin(subEventContainer, new Insets(15));

                Label titleLabel = new Label(subEvent.getName());
                titleLabel.getStyleClass().add("title");

                Label descriptionLabel = new Label(subEvent.getDescription());
                descriptionLabel.getStyleClass().add("custom-label");

                Label beginDateLabel = new Label("Data inicial");
                beginDateLabel.getStyleClass().add("caption");

                Label beginDateValue = new Label(subEvent.getBeginDate().toString());
                beginDateValue.getStyleClass().add("subcaption");

                Label endDateLabel = new Label("Data Final");
                endDateLabel.getStyleClass().add("caption");

                Label endDateValue = new Label(subEvent.getEndDate().toString());
                endDateValue.getStyleClass().add("subcaption");

                Label locationLabel = new Label("Local");
                locationLabel.getStyleClass().add("caption");

                Button editSubEventButton = new Button("Editar SubEvento");
                editSubEventButton.getStyleClass().add("custom-button");
                editSubEventButton.setOnAction(e -> {
                    try {
                        handleEditSubEventButton(subEvent);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Button seeSessionsButton = new Button("Ver Sessões");
                seeSessionsButton.getStyleClass().add("custom-button");
                seeSessionsButton.setOnAction(e -> {
                    try {
                        handleSeeSession(subEvent);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                VBox descriptionBox = new VBox(5, titleLabel, descriptionLabel);
                VBox beginDateBox = new VBox(5, beginDateLabel, beginDateValue);
                VBox endDateBox = new VBox(5, endDateLabel, endDateValue);

                HBox infoBox = new HBox(50, beginDateBox, endDateBox);
                HBox bottomBox = new HBox(50, editSubEventButton, seeSessionsButton);
                infoBox.setAlignment(Pos.CENTER_LEFT);
                bottomBox.setAlignment(Pos.CENTER);
                VBox containerBox = new VBox(45,descriptionBox, infoBox, bottomBox);


                subEventContainer.getChildren().addAll(containerBox);
                mainContainer.getChildren().add(subEventContainer);
                mainContainer.setAlignment(Pos.CENTER);


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

    @FXML
    private void handleEditSubEventButton(SubEventInterface subEvent) throws IOException {
        SceneLoader.setSubEventData(subEvent);
        SceneLoader.loadScene("/org/upe/ui/telaEditarSubEvento.fxml", "Editar SubEvento", mySubEventsPage);
    }

    @FXML
    private void handleSeeSession(SubEventInterface subEvent) throws IOException {
        SceneLoader.setSubEventData(subEvent);
        SceneLoader.loadScene("/org/upe/ui/telaSessao.fxml", "Sessões", mySubEventsPage);
    }

}
