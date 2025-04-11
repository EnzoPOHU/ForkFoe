package com.forkfoe.forkfoe.controller;

import com.forkfoe.forkfoe.util.ServiceTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class MainController {
    @FXML
    private Label serviceTimer;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button resetButton;

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            serviceTimer.setText(ServiceTimer.getElapsedTime());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        String view = (String) ((Node)event.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }

    private void loadFXML(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        mainBorderPane.setCenter(loader.load());
    }

    @FXML
    public void onStartButtonClicked() {
        ServiceTimer.start();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        resetButton.setDisable(false);
    }

    @FXML
    public void onPauseButtonClicked() {
       ServiceTimer.pause();
       startButton.setDisable(false);
       stopButton.setDisable(true);
       resetButton.setDisable(false);
    }

    @FXML
    public void onResetButtonClicked() {
        ServiceTimer.reset();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        resetButton.setDisable(true);
    }
}
