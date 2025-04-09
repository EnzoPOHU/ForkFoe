package com.forkfoe.forkfoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Objects;

public class Dish extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/forkfoe/forkfoe/DishView.fxml")))    ;
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/forkfoe/forkfoe/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Commande de Plats");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
