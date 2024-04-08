module com.po2.jukebox {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.po2.jukebox to javafx.fxml;
    opens com.po2.jukebox.domain to javafx.base;
    exports com.po2.jukebox;
    requires javafx.graphicsEmpty;
}
