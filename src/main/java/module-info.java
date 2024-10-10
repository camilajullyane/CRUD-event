module crudEvent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens org.upe.ui to javafx.fxml;
    exports org.upe.ui;
}