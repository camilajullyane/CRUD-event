module crudEvent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;

    opens org.upe.ui to javafx.fxml;
    exports org.upe.ui;
}