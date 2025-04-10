    package com.forkfoe.forkfoe;

    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.image.Image;
    import javafx.stage.Stage;

    import java.io.IOException;


    public class HelloApplication extends Application {

        private static final String []initQueries = {
            "CREATE TABLE IF NOT EXISTS restaurantTable (" +
                    "id INTEGER PRIMARY KEY, " +
                    "seat INTEGER NOT NULL," +
                    "number INTEGER NOT NULL)",

            "CREATE TABLE IF NOT EXISTS client (" +
                    "id INTEGER PRIMARY KEY, " +
                    "table_number INTEGER NOT NULL)",

            "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR NOT NULL, " +
                    "role VARCHAR NOT NULL, " +
                    "worked_time INTEGER NOT NULL)",

            "CREATE TABLE IF NOT EXISTS tableOrder (" +
                    "id INTEGER PRIMARY KEY," +
                    "bill INTEGER," +
                    "status VARCHAR)",

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
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/forkfoe/forkfoe/DishView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            scene.getStylesheets().add(HelloApplication.class.getResource("/com/forkfoe/forkfoe/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("commande de Plats!");
            stage.show();
        }

        public static void main(String[] args) {
            SQLiteWrapper.executeBatch(initQueries);
            launch();
        }
    }