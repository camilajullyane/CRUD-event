package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import javafx.scene.text.Text;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.util.List;

public class BeginController {
    @FXML
    Button subscriptionButton;

    @FXML
    Text infoText;

    @FXML
    Button createEventButton;

    @FXML
    StackPane beginPage;

    @FXML
    Button logOutButton;
  
    @FXML
    Text userName;

    @FXML
    private void initialize(){
        EventController eventController = new EventController();
        List<EventInterface> events = eventController.getAllEvents();

        if (UserSession.getInstance().getCurrentUser() != null) {
            userName.setText(UserSession.getInstance().getCurrentUser().getName());
        }

        if(events.isEmpty()){
            infoText.setText("Você ainda não faz parte de nenhum evento");
        } else {
            infoText.setText("Você faz parte de " + events.size() + " eventos");
        }
    }

    @FXML
    private void handleSubscription() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", beginPage);
    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", beginPage);
    }

    @FXML
    private void handleScheduleButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", beginPage);
    }

    @FXML
    private void createNewEvent() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCriandoEvento.fxml", "Criar Evento", beginPage);
    }
}
