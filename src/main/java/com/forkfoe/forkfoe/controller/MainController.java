package com.forkfoe.forkfoe.controller;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        String view = (String) ((Node)event.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }

    private void loadFXML(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        mainBorderPane.setCenter(loader.load());
    }
}
