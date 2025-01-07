module org.example.moviecollection {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.moviecollection to javafx.fxml;
    exports org.example.moviecollection;
}