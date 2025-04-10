package com.forkfoe.forkfoe.controller.table;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class TableGestionController {

    @FXML
    private VBox tablesList;

    @FXML
    private Label noTableLabel;

    /**
     * Ajouter une carte de table dans l'interface.
     *
     * @param tableNumber     Numéro de la table
     * @param maxSeats        Nombre de sièges maximum
     * @param reservationName Nom de la réservation (ou NULL si aucune réservation)
     */

    public void addTableCard(String tableNumber, int maxSeats, String reservationName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/table/TableCard.fxml"));
            HBox tableCard = loader.load();

            TableCardController controller = loader.getController();
            controller.setTableCardDetails(tableNumber, maxSeats, reservationName);

            tablesList.getChildren().add(tableCard);

            updateNoTableLabelVisibility();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout de la carte de table.");
        }
    }

    private void updateNoTableLabelVisibility() {
        noTableLabel.setVisible(tablesList.getChildren().isEmpty());
    }

    @FXML
    public void onAddTableButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/table/AddTableForm.fxml"));
            VBox addTableForm = loader.load();

            AddTableController addTableController = loader.getController();
            addTableController.setTableGestionController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter une table");
            stage.setScene(new Scene(addTableForm));
            stage.initOwner(tablesList.getScene().getWindow());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du formulaire d'ajout de table.");
        }
    }

    @FXML
    public void initialize() {
        updateNoTableLabelVisibility();
    }
}