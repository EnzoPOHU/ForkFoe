package com.forkfoe.forkfoe.controller.table;

import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import com.forkfoe.forkfoe.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class TableGestionController {

    public Button addTableButton;
    public Button showAvailableTablesButton;
    public GridPane tableContainer;
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
            AlertUtil.showError(
                    "Impossible de charger la table",
                    "Erreur de chargement de la table"
            );
        }
    }


    private void loadTablesFromRepository() {
        List<Table> tables = TableRepository.getTables();
        for (Table table : tables) {
            addTableCard(String.valueOf(table.number()), table.maxSeats(), table.reservationName());
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
            AlertUtil.showError(
                    "Impossible de charger le formulaire d'ajout de table",
                    "Erreur du formulaire d'ajout"
            );
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
                .filter(table -> "Aucune réservation".equalsIgnoreCase(table.reservationName()))
                .toList();

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Tables disponibles");
        dialog.setHeaderText("Liste des tables actuellement disponibles :");

        ListView<String> tableListView = new ListView<>();
        tableListView.getItems().addAll(
                tables.stream()
                        .map(table -> "Table #" + table.number())
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