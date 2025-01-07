module org.example.moviecollection {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.moviecollection to javafx.fxml;
    exports org.example.moviecollection;
    exports org.example.moviecollection.gui;
    opens org.example.moviecollection.gui to javafx.fxml;
}