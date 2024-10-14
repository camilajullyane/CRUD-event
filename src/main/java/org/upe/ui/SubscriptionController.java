package org.upe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.upe.utils.SceneLoader;

import java.io.IOException;

public class SubscriptionController {
    @FXML
    Button homeButton;

    @FXML
    Button logOutButton;

    @FXML
    StackPane mySubscriptionPage;

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", mySubscriptionPage);
    }

    @FXML
    private void homePage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", mySubscriptionPage);
    }
}
