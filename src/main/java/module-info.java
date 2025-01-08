module org.example.moviecollection {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;


    opens org.example.moviecollection to javafx.fxml;
    exports org.example.moviecollection;
    exports org.example.moviecollection.gui.controllers;
    opens org.example.moviecollection.gui to javafx.fxml;
    opens org.example.moviecollection.gui.controllers to javafx.fxml;
}