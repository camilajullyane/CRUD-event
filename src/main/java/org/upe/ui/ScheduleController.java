package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    EventController eventController = new EventController();

    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox mainContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showEvents();
    }

    private void showEvents() {
        List<EventInterface> events = eventController.getAllEvents();

        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);

        events.forEach(event -> {
            Label title = new Label("PETROLINA - \"Diabetes em Foco: Alimentação e Estratégias Nutricionais para o Controle e Prevenção\"");
            title.setFont(Font.font("Arial", 18));
            title.setTextFill(Color.web("#00FFA3"));

            // Descrição do evento
            Label description = new Label("Explorar a relação entre a alimentação e os níveis de glicose no sangue, e como isso afeta o controle do diabetes...");
            description.setFont(Font.font("Arial", 14));
            description.setTextFill(Color.WHITE);
            description.setWrapText(true);

            // Informações de data e hora
            HBox infoBox = new HBox(15);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            // Ícone de calendário (substitua por uma imagem real ou um ícone de biblioteca)
            Image calendarIcon = new Image("file:calendar-icon.png");  // Coloque o caminho da sua imagem
            ImageView calendarImageView = new ImageView(calendarIcon);
            calendarImageView.setFitHeight(20);
            calendarImageView.setFitWidth(20);

            Label dateLabel = new Label("Quarta-feira, 25 de outubro de 2024");
            dateLabel.setFont(Font.font("Arial", 14));
            dateLabel.setTextFill(Color.WHITE);

            // Ícone de relógio
            Image clockIcon = new Image("file:clock-icon.png");  // Coloque o caminho da sua imagem
            ImageView clockImageView = new ImageView(clockIcon);
            clockImageView.setFitHeight(20);
            clockImageView.setFitWidth(20);

            Label timeLabel = new Label("08:00 - 10:00");
            timeLabel.setFont(Font.font("Arial", 14));
            timeLabel.setTextFill(Color.WHITE);

            // Ícone de local
            Image locationIcon = new Image("file:location-icon.png");  // Coloque o caminho da sua imagem
            ImageView locationImageView = new ImageView(locationIcon);
            locationImageView.setFitHeight(20);
            locationImageView.setFitWidth(20);

            Label locationLabel = new Label("UPE Petrolina - SALA 08 - BLOCO B");
            locationLabel.setFont(Font.font("Arial", 14));
            locationLabel.setTextFill(Color.WHITE);

            // Adicionando os ícones e textos na HBox
            infoBox.getChildren().addAll(calendarImageView, dateLabel, clockImageView, timeLabel, locationImageView, locationLabel);

            // Botões
            HBox buttonBox = new HBox(15);
            buttonBox.setAlignment(Pos.CENTER);
            Button moreInfoButton = new Button("Mais informações");
            moreInfoButton.setStyle("-fx-background-color: #00FFA3; -fx-text-fill: black; -fx-font-size: 14px; -fx-background-radius: 10;");

            Button registerButton = new Button("Realizar Inscrição");
            registerButton.setStyle("-fx-background-color: #00FFA3; -fx-text-fill: black; -fx-font-size: 14px; -fx-background-radius: 10;");

            buttonBox.getChildren().addAll(moreInfoButton, registerButton);

            // Adicionando os elementos ao container principal
            mainContainer.getChildren().addAll(title, description, infoBox, buttonBox);
        });

        scrollPane.setContent(mainContainer);
    }
}