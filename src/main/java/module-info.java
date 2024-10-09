module crudEvent {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.upe.ui to javafx.fxml;
    exports org.upe.ui;
}