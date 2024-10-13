package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    public AnchorPane loginPage;
    @FXML
    Button signInButton;
   @FXML
    private void handleLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaInicio.fxml"));
        AnchorPane screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage) loginPage.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Even4");
    }

}
