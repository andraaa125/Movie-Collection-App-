package org.example.moviecollection.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MovieCollectionApplicationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}