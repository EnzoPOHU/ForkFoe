package com.forkfoe.forkfoe.controller.table;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.forkfoe.forkfoe.SQLiteWrapper;

public class EditTableController {

    @FXML
    private TextField tableNumberInput;

    @FXML
    private Spinner<Integer> maxSeatsInput;

    @FXML
    private TextField reservationNameInput;

    private int originalTableNumber;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1); // Min: 1, Max: 20, Valeur initiale: 1
        maxSeatsInput.setValueFactory(valueFactory);
    }

    public void setTableDetails(int tableNumber, int maxSeats, String reservationName) {
        this.originalTableNumber = tableNumber;

        tableNumberInput.setText(String.valueOf(tableNumber));
        maxSeatsInput.getValueFactory().setValue(maxSeats);
        reservationNameInput.setText(reservationName);
    }

    @FXML
    public void onSaveClicked() {
        int updatedTableNumber = Integer.parseInt(tableNumberInput.getText());
        int updatedMaxSeats = maxSeatsInput.getValue();
        String updatedReservation = reservationNameInput.getText();

        String query = "UPDATE restaurantTable SET number = ?, seat = ?, reservationName = ? WHERE number = ?;";
        SQLiteWrapper.execute(query, updatedTableNumber, updatedMaxSeats, updatedReservation, originalTableNumber);

        closeWindow();
    }

    @FXML
    public void onCancelClicked() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) tableNumberInput.getScene().getWindow();
        stage.close();
    }
}