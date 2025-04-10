package com.forkfoe.forkfoe.controller;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import com.forkfoe.forkfoe.util.AlertUtil;
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

    private TableManagerController tableGestionController;

    public void setTableGestionController(TableManagerController controller) {
        this.tableGestionController = controller;
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        maxSeatsInput.setValueFactory(valueFactory);
    }

    @FXML
    public void onSaveTableClicked() {
        String tableNumber = tableNumberInput.getText();
        int maxSeats = maxSeatsInput.getValue();

        if (tableNumber.isEmpty() || maxSeats <= 0) {
            AlertUtil.showError(
                    "Tous les champs doivent être remplis",
                    "Champs invalides"
            );
            return;
        }

        Table newTable = new Table(Integer.parseInt(tableNumber), maxSeats, "Aucune réservation");
        TableRepository.addTable(newTable);

        tableGestionController.addTableCard(String.valueOf(newTable.number()), maxSeats, "Aucune réservation");
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

    @FXML
    protected void onTypingTableNumber() {
        try {
            Integer.parseInt(tableNumberInput.getText());
        } catch (NumberFormatException e) {
            tableNumberInput.clear();
        }
    }
}