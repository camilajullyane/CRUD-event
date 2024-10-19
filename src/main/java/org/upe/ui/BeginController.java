package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.upe.utils.SceneLoader;
import javafx.scene.text.Text;
import org.upe.utils.UserSession;

import java.io.IOException;

public class BeginController {
    @FXML
    Button subscriptionButton;

    @FXML
    StackPane beginPage;

    @FXML
    Button LogOutButton;
  
    @FXML
    Text userName;

    @FXML

    private void initialize() throws IOException {
        if (UserSession.getInstance().getCurrentUser() != null) {
            userName.setText(UserSession.getInstance().getCurrentUser().getName());
//            userName.setVisible(true);
        }
    }


    @FXML
    private void logOut() throws IOException {
      
    }
}
