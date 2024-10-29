package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.upe.controllers.ArticleController;
import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyArticlesController implements Initializable {
    ArticleController articleController = new ArticleController();
    private static final String FONT_STYLE_BOLD_ITALIC = "System Bold Italic";
    private static final String FONT_SYSTEM_ITALIC = "System Italic";
    private static final Paint color = Color.web("#cdc7c7");


    @FXML
    private StackPane myArticlesPage;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMyArticles();
    }

    private void showMyArticles() {
        List<ArticleInterface> articles = articleController.getAllArticlesByUser(UserSession.getInstance().getCurrentUser().getCPF());

        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);

        if (articles.isEmpty()) {
            VBox articleContainer = new VBox();
            articleContainer.setPrefWidth(350);
            articleContainer.setPrefHeight(260);
            articleContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
            articleContainer.setSpacing(10);
            articleContainer.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            articleContainer.setAlignment(Pos.CENTER);

            Label title = new javafx.scene.control.Label("Você não tem artigos criados");
            title.setFont(javafx.scene.text.Font.font("Roboto", 16));
            title.setTextFill(javafx.scene.paint.Color.WHITE);
            VBox.setVgrow(title, Priority.ALWAYS);
            title.setMaxWidth(Double.MAX_VALUE);
            title.setAlignment(Pos.CENTER);

            articleContainer.getChildren().add(title);

            mainContainer.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(articleContainer);
        } else {
            articles.forEach(article -> {
                VBox articleContainer = new VBox();
                articleContainer.setPrefWidth(557);
                articleContainer.setPrefHeight(260);
                articleContainer.setMinHeight(200);
                articleContainer.setMaxHeight(260);
                articleContainer.setStyle("-fx-background-color: #4E4E55; -fx-background-radius: 25;");
                articleContainer.setSpacing(10);
                articleContainer.setPadding(new Insets(10, 10, 10, 10));
                articleContainer.setAlignment(Pos.CENTER);
                VBox.setVgrow(articleContainer, Priority.ALWAYS);

                Label titleLabel = new javafx.scene.control.Label(article.getName());
                titleLabel.setPrefHeight(100);
                titleLabel.setPrefWidth(557);
                titleLabel.setStyle("-fx-text-fill: #2dd4bf;" +
                        "-fx-font-weight: bold;" +
                        "-fx-alignment: top-center;" +
                        "-fx-translate-y: -30;");
                titleLabel.setFont(javafx.scene.text.Font.font(FONT_STYLE_BOLD_ITALIC, 16));
                titleLabel.setAlignment(Pos.CENTER);
                titleLabel.setWrapText(true);

                Label descriptionLabel = new javafx.scene.control.Label(article.getArticleAbstract());
                descriptionLabel.setPrefHeight(20);
                descriptionLabel.setPrefWidth(550);
                descriptionLabel.setStyle("-fx-translate-x: 10;" +
                        "-fx-translate-y: -95");
                descriptionLabel.setTextFill(color);
                descriptionLabel.setAlignment(Pos.CENTER_LEFT);
                descriptionLabel.setWrapText(true);


                articleContainer.getChildren().addAll(titleLabel, descriptionLabel);

                mainContainer.getChildren().add(articleContainer);
            });
        }

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }


    @FXML
    private void moveToSettingsScreen () throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaConfiguracoes.fxml", "Configurações", myArticlesPage);

    }

    @FXML
    private void logOut() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/start-app.fxml", "Even2", myArticlesPage);
    }

    @FXML
    private void moveToScheduleScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaProgramacao.fxml", "Programação", myArticlesPage);
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", myArticlesPage);
    }

    @FXML
    private void moveToSubscriptionButton() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", myArticlesPage);
    }

    @FXML
    private void moveToSubmissionsPage() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaSubmissões.fxml", "Submissões", myArticlesPage);
    }

    @FXML
    private void backToCertificate() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaCertificado.fxml", "Submissões", myArticlesPage);
    }

}
