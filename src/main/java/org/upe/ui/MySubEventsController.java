package org.upe.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.upe.controllers.SubEventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MySubEventsController {
    SubEventController subEventController = new SubEventController();
    private static final String FONT_STYLE_BOLD_ITALIC = "System Bold Italic";
    private static final String FONT_SYSTEM_ITALIC = "System Italic";
    private static final Paint color = Color.web("#cdc7c7");
    private static final String CAPTION_STYLE_CLASS = "caption";
    private static final String SUBCAPTION_STYLE_CLASS = "subcaption";

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane mySubEventsPage;

    public void initialize() {
        showMySubEvents();
    }

    private void showMySubEvents() {
        EventInterface currentEvent = SceneLoader.getEventData();
        ArrayList<SubEventInterface> subEvents = subEventController. getAllSubEventsByEvent(currentEvent.getId());

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

                Label dateLabel = new Label("Data");
                dateLabel.getStyleClass().add(CAPTION_STYLE_CLASS);

                Label dateValue = new Label(subEvent.getDate());
                dateValue.getStyleClass().add(SUBCAPTION_STYLE_CLASS);


                Label locationLabel = new Label("Local");
                locationLabel.getStyleClass().add(CAPTION_STYLE_CLASS);

                Label locationValue = new Label(subEvent.getLocal());
                locationValue.getStyleClass().add(SUBCAPTION_STYLE_CLASS);

                Label speakersLabel = new Label("Palestrantes do Evento");
                speakersLabel.getStyleClass().add(CAPTION_STYLE_CLASS);

                Label speakersValue = new Label(subEvent.getSpeakers());
                speakersValue.getStyleClass().add(SUBCAPTION_STYLE_CLASS);

                Button editSubEventButton = new Button("Editar SubEvento");
                editSubEventButton.getStyleClass().add("custom-button");
                editSubEventButton.setOnAction(e -> {
                    try {
                        handleEditSubEvent(subEvent.getId());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                VBox descriptionBox = new VBox(5, titleLabel, descriptionLabel);
                VBox dateBox = new VBox(5, dateLabel, dateValue);
                VBox locationBox = new VBox(5, locationLabel, locationValue);
                VBox ownerBox = new VBox(5, speakersLabel, speakersValue);


                HBox infoBox = new HBox(50, dateBox, locationBox, ownerBox);
                HBox bottomBox = new HBox(50, editSubEventButton);
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
    private void handleEditSubEvent(String subEventID) throws IOException {
//        SceneLoader.loadScene("/org/upe/ui/editSubEvent.fxml", "Editar SubEvento", mySubEventsPage);
    }

}
