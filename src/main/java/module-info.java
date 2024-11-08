module crudEvent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jakarta.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires io.github.cdimascio.dotenv.java;

    opens org.upe.ui to javafx.fxml;
    exports org.upe.ui;
    exports org.upe.persistence.model;

    opens org.upe.persistence.model to jakarta.persistence, org.hibernate.orm.core;
}