package com.forkfoe.forkfoe.controller.table;

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

        tableNumberLabel.setText("Table #: " + tableNumber);
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
            editorController.setParentController(parentController); // 👈 on passe le parent ici

            Stage stage = new Stage();
            stage.setTitle("Modifier la table #" + tableNumber);
            stage.setScene(new Scene(editTableForm));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ouverture du formulaire d'édition de table.");
        }
    }

}