package org.upe.ui;

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
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.SessionInterface;
import org.upe.persistence.interfaces.SubEventInterface;
import org.upe.utils.SceneLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SessionController implements Initializable {
    private final FacadeInterface facade = new Facade();

    @FXML
    ScrollPane scrollPane;

    @FXML
    private StackPane sessionPage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showSessions();
    }

    private void showSessions() {
        EventInterface currentEvent = SceneLoader.getEventData();

        List<SessionInterface> sessions = currentEvent.getSubEvents().getFirst().getAllSessions();

        scrollPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/custom.css")).toExternalForm());
        scrollPane.getStyleClass().add("custom-scroll-pane");
        VBox mainContainer = new VBox();
        mainContainer.setSpacing(20); // Espaço entre eventos
        mainContainer.setPadding(new Insets(20));

        mainContainer.getChildren().clear();

        if (sessions.isEmpty()) {
            Label noEventLabel = new Label("Ainda não há sessões disponíveis");
            noEventLabel.getStyleClass().add("custom-label");
            noEventLabel.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(noEventLabel);
        } else {
            sessions.forEach(event -> {
                VBox eventBlock = new VBox();
                eventBlock.getStyleClass().add("event-block");
                eventBlock.setPadding(new Insets(10));
                eventBlock.setSpacing(10);

                // Formatação do horário
                Label timeLabel = new Label(event.getBeginHour() + " - " + event.getEndHour());
                timeLabel.getStyleClass().add("event-time");

                // Título do evento
                Label titleLabel = new Label(event.getName());
                titleLabel.getStyleClass().add("event-title");

                // Descrição
                Label descriptionLabel = new Label(event.getDescription());
                descriptionLabel.getStyleClass().add("event-description");

                // Palestrante
                Label speakerLabel = new Label("Palestrante: " + event.getSpeaker());
                speakerLabel.getStyleClass().add("event-speaker");

                // Adicionando ao bloco
                eventBlock.getChildren().addAll(timeLabel, titleLabel, descriptionLabel, speakerLabel);

                // Adiciona o bloco ao container principal
                mainContainer.getChildren().add(eventBlock);
            });
        }

        scrollPane.setContent(mainContainer);
    }

    @FXML
    private void moveToSettingsScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", sessionPage);

    }

    @FXML
    void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", sessionPage);
    }

    @FXML
    private void moveToHomeScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", sessionPage);
    }

    @FXML
    private void moveToScheduleScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", sessionPage);
    }

    @FXML
    private void moveToSubmissionsPage () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", sessionPage);
    }


    @FXML
    private void moveToSubscriptionButton () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", sessionPage);
    }

    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", sessionPage);
    }

    @FXML
    private void handleSeeSession(SubEventInterface subEvent) throws IOException {
        SceneLoader.setSubEventData(subEvent);
        SceneLoader.loadScene("/org/upe/ui/telaSessao.fxml", "Sessões", sessionPage);
    }
}
