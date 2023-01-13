module com.example.guisocialnetwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.guisocialnetwork to javafx.fxml;
    exports com.example.guisocialnetwork;
    exports com.example.guisocialnetwork.domain;

}