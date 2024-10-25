package org.upe.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.upe.persistence.interfaces.EventInterface;

import java.io.IOException;

public class SceneLoader {

    private static EventInterface eventData;

    private SceneLoader() {
        throw new UnsupportedOperationException("Utility class");
    }


    public static void loadScene(String path, String title, StackPane currentPage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(path));
        StackPane screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage) currentPage.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
    }

    public static void setEventData(EventInterface data) {
        eventData = data;
    }

    public static EventInterface getEventData() {
        return eventData;
    }
}
