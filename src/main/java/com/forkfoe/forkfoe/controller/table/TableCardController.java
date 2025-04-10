package com.forkfoe.forkfoe.controller.table;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TableCardController {

    @FXML
    private Label tableNumberLabel;

    @FXML
    private Label seatsLabel;

    @FXML
    private Label reservationNameLabel;

    public void setTableCardDetails(String tableNumber, int seats, String reservationName) {
        tableNumberLabel.setText("Table #: " + tableNumber);
        seatsLabel.setText("Places: " + seats);
        reservationNameLabel.setText(reservationName.isEmpty() ? "Aucune réservation" : "Réservée par : " + reservationName);
    }
}