package com.forkfoe.forkfoe;

import com.forkfoe.forkfoe.util.SQLiteWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ForkFoeApplication extends Application {
    private static final String []initQueries = {
        "CREATE TABLE IF NOT EXISTS restaurantTable (" +
                "id INTEGER PRIMARY KEY, " +
                "seat INTEGER NOT NULL," +
                "number INTEGER NOT NULL," +
                "reservationName VARCHAR NOT NULL)",

        "CREATE TABLE IF NOT EXISTS client (" +
                "id INTEGER PRIMARY KEY, " +
                "table_number INTEGER NOT NULL)",

        "CREATE TABLE IF NOT EXISTS employee (" +
                "id INTEGER PRIMARY KEY, " +
                "name VARCHAR NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "role VARCHAR NOT NULL, " +
                "worked_time INTEGER NOT NULL)",

        "CREATE TABLE IF NOT EXISTS tableOrder (" +
                "id INTEGER PRIMARY KEY," +
                "bill INTEGER NOT NULL," +
                "status VARCHAR NOT NULL," +
                "table_id INTEGER NOT NULL)",

        "CREATE TABLE IF NOT EXISTS tableDish (" +
                "id INTEGER PRIMARY KEY, " +
                "name VARCHAR, " +
                "description TEXT, " +
                "price INTEGER, " +
                "img BLOB)",

        "CREATE TABLE IF NOT EXISTS orderDish (" +
                "id INTEGER PRIMARY KEY, " +
                "orderID INTEGER, dishID INTEGER, " +
                "quantity INTEGER)",
    };

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ForkFoeApplication.class.getResource("/com/forkfoe/forkfoe/fxml/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("ForkFoe");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        SQLiteWrapper.executeBatch(initQueries);
        launch();
    }
}