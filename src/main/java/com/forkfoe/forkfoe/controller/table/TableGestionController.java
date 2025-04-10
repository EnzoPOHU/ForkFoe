package com.forkfoe.forkfoe.controller.table;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class TableGestionController {

    @FXML
    private VBox tablesList;

    @FXML
    private Label noTableLabel;


    public void addTableCard(String tableNumber, int maxSeats, String reservationName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/forkfoe/forkfoe/fxml/table/TableCard.fxml"));
            HBox tableCard = loader.load();

            TableCardController controller = loader.getController();
            controller.setTableCardDetails(tableNumber, maxSeats, reservationName);
            controller.setParentController(this);

            tablesList.getChildren().add(tableCard);
            updateNoTableLabelVisibility();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout de la carte de table.");
        }
    }


    private void loadTablesFromRepository() {
        List<Table> tables = TableRepository.getTables();
        for (Table table : tables) {
            addTableCard(String.valueOf(table.getNumber()), table.getMaxSeats(), table.getReservationName());
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
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du formulaire d'ajout de table.");
        }
    }

    public void refreshTableCards() {
        tablesList.getChildren().clear();
        loadTablesFromRepository();
        updateNoTableLabelVisibility();
    }

    @FXML
    public void onShowAvailableTablesClicked() {
        List<Table> tables = TableRepository.getTables().stream()
                .filter(table -> "Aucune réservation".equalsIgnoreCase(table.getReservationName()))
                .collect(Collectors.toList());

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Tables disponibles");
        dialog.setHeaderText("Liste des tables actuellement disponibles :");

        ListView<String> tableListView = new ListView<>();
        tableListView.getItems().addAll(
                tables.stream()
                        .map(table -> "Table #" + table.getNumber())
                        .toList()
        );

        dialog.getDialogPane().setContent(tableListView);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        dialog.showAndWait();
    }


    @FXML
    public void initialize() {
        loadTablesFromRepository();
        updateNoTableLabelVisibility();
    }
}