package org.upe.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.upe.controllers.ArticleController;
import org.upe.facade.Facade;
import org.upe.facade.FacadeInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;


public class CreateArticleController {
    private final FacadeInterface facade = new Facade();

    @FXML
    public Button settingsButton;

    @FXML
    private TextField articleName;

    @FXML
    private TextField articleText;

    @FXML
    private Text errorMessage;

    @FXML
    private StackPane createArticle;

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


        UserSession userSession = UserSession.getInstance();

        String name = articleName.getText();
        String articleAbstract = articleText.getText();

        facade.createArticle(userSession.getCurrentUser(), name, articleAbstract);

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
    @FXML
    private void moveToMyEventsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/MyEvents.fxml", "Configurações", createArticle);
    }
}
