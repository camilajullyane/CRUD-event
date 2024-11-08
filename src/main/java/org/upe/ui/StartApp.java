package org.upe.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.upe.persistence.DAO.UserDAO;
import org.upe.persistence.model.User;

import java.io.IOException;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("start-app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Even2");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//
//        User user = new User();
//        user.setName("Teste65465");
//        user.setCpf("12345678dsdsfa65");
//        user.setEmail("tesfdasfasdte565@gmail.com");
//        user.setPassword("123456");
//        userDAO.create(user);
//        System.out.println("user created");
        launch();
    }
}