module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;
    requires jaudiotagger;

    opens com.example to javafx.fxml;
    exports com.example;
}
