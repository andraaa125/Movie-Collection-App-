package org.example.moviecollection.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.moviecollection.MovieCollectionApplication;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;



public class MovieCollectionApplicationController implements Initializable {




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void onSearchBtnClick(ActionEvent actionEvent) {

    }

    public void onPlayBtnClick(ActionEvent actionEvent) {

    }

    public void onAddCategoryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditCategoryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("CategoryEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditCategoryController categoryController = fxmlLoader.getController();
        categoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Category");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteCategoryClick(ActionEvent actionEvent) {
    }

    public void onAddMovieClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Movie");
        stage.setScene(scene);
        stage.show();
    }

    public void onEditMovieClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieCollectionApplication.class.getResource("MovieEditor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddEditMovieController movieController = fxmlLoader.getController();
        movieController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Movie");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteMovieClick(ActionEvent actionEvent) {
    }
}