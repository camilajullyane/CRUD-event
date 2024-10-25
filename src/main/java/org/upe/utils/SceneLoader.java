package org.upe.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public static void loadScene(String path, String title, StackPane currentPage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(path));
        StackPane screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage) currentPage.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
    }
}
