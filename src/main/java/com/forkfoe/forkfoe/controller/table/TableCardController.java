package com.forkfoe.forkfoe.controller.table;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import com.forkfoe.forkfoe.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableCardController {

    @FXML
    private Label tableNumberLabel;

    @FXML
    private Label seatsLabel;

    @FXML
    private Label reservationNameLabel;

    private int tableNumber;
    private int seats;
    private String reservationName;

    private TableGestionController parentController;

    public void setParentController(TableGestionController controller) {
        this.parentController = controller;
    }


    public void setTableCardDetails(String tableNumber, int seats, String reservationName) {
        this.tableNumber = Integer.parseInt(tableNumber);
        this.seats = seats;
        this.reservationName = (reservationName == null || reservationName.isBlank())
                ? "Aucune réservation"
                : reservationName;

        tableNumberLabel.setText("Table n° " + tableNumber);
        seatsLabel.setText("Places: " + seats);
        reservationNameLabel.setText(this.reservationName);
    }

    @FXML
    private void onTableCardClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/table/EditTableForm.fxml"));
            VBox editTableForm = loader.load();

            EditTableController editorController = loader.getController();
            editorController.setTableDetails(tableNumber, seats, reservationName);
            editorController.setParentController(parentController);

            Stage stage = new Stage();
            stage.setTitle("Modifier la table #" + tableNumber);
            stage.setScene(new Scene(editTableForm));
            stage.show();

        } catch (Exception e) {
            AlertUtil.showError(
                    "Impossible d'ouvrir le formulaire d'édition",
                    "Erreur du formulaire d'édition"
            );
        }
    }


    @FXML
    private void onLibererButtonClicked() {
        this.reservationName = "Aucune réservation";
        reservationNameLabel.setText(this.reservationName);

        Table updatedTable = new Table(tableNumber, seats, "Aucune réservation");
        TableRepository.updateTable(updatedTable, tableNumber);
        parentController.refreshTableCards();
    }

}