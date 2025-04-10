package com.forkfoe.forkfoe.controller.table;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTableController {

    @FXML
    private TextField tableNumberInput;

    @FXML
    private Spinner<Integer> maxSeatsInput;

    private TableGestionController tableGestionController;

    public void setTableGestionController(TableGestionController controller) {
        this.tableGestionController = controller;
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1); // Min: 1, Max: 20, Valeur initiale: 1
        maxSeatsInput.setValueFactory(valueFactory);
    }

    @FXML
    public void onSaveTableClicked() {
        String tableNumber = tableNumberInput.getText();
        int maxSeats = maxSeatsInput.getValue();

        if (tableNumber.isEmpty() || maxSeats <= 0) {
            System.err.println("Tous les champs doivent être remplis !");
            return;
        }

        int tableNum = Integer.parseInt(tableNumber);

        Table newTable = new Table(tableNum, maxSeats, "Aucune réservation");
        TableRepository.addTable(newTable);

        tableGestionController.addTableCard(String.valueOf(newTable.getNumber()), maxSeats, "Aucune réservation");
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