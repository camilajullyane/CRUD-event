package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.upe.controllers.ArticleController;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;


public class CreateArticleCotroller {
    @FXML
    public Button settingsButton;

    @FXML
    private TextField articleKeyWords;

    @FXML
    private TextField articleName;

    @FXML
    private Button articleSave;

    @FXML
    private TextField articleText;

    @FXML
    private Text errorMessage;

    @FXML
    private Button certificateButton;

    @FXML
    private StackPane createArticle;

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private Button subscriptionButton;

    @FXML
    private void moveToSettingsScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", createArticle);
    }

    @FXML
    void handleArticle() {
        if(articleName.getText().isEmpty() || articleText.getText().isEmpty()){
            errorMessage.setVisible(true);
            return;
        }

        ArticleController articleController = new ArticleController();
        UserSession userSession = UserSession.getInstance();

        String name = articleName.getText();
        String articleAbstract = articleText.getText();

        articleController.createArticle(userSession.getCurrentUser(), name,articleAbstract);

        articleName.setText("");
        articleText.setText("");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("Artigo criado com sucesso!");
        alert.showAndWait();
    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", createArticle);
    }

    @FXML
    private void handleScheduleButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", createArticle);
    }

    @FXML
    private void handleHomeButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Início", createArticle);
    }

    @FXML
    private void handleSubscriptionButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Programação", createArticle);
    }

    @FXML
    private void handleCertificatonButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Certificados", createArticle);
    }
}
