package com.forkfoe.forkfoe.controller.table;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import com.forkfoe.forkfoe.util.AlertUtil;
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
    private TableGestionController parentController;

    public void setParentController(TableGestionController controller) {
        this.parentController = controller;
        System.out.println("ParentController défini : " + (parentController != null));
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
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

        if (parentController != null) {
            parentController.refreshTableCards();
        }
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
    public void onDeleteClicked() {
        System.out.println("Suppression d'une table en cours...");
        Table tableToRemove = new Table(originalTableNumber, maxSeatsInput.getValue(), reservationNameInput.getText());
        TableRepository.removeTable(tableToRemove);

        try {
            if (parentController != null) {
                System.out.println("Appel à refreshTableCards à partir de onDeleteClicked...");
                parentController.refreshTableCards();
            } else {
                System.out.println("parentController est null !");
            }
        } catch (Exception e) {
            AlertUtil.showError(
                    "Erreur lors de la suppression de la table",
                    "Erreur de suppression"
            );
        }

        closeWindow();
    }
}