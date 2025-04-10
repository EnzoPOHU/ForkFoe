package com.forkfoe.forkfoe.controller.table;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

        Table updatedTable = new Table(updatedTableNumber, updatedMaxSeats, updatedReservation);
        TableRepository.updateTable(updatedTable, originalTableNumber);

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