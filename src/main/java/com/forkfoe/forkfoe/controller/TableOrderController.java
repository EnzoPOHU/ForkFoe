package com.forkfoe.forkfoe.controller;

import com.forkfoe.forkfoe.repository.TableOrderRepository;
import com.forkfoe.forkfoe.model.TableOrder;
import com.forkfoe.forkfoe.model.Table;
import com.forkfoe.forkfoe.repository.TableRepository;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class TableOrderController {

    @FXML
    private TableView<TableOrder> ordersTableView;

    @FXML
    private TableColumn<TableOrder, Integer> idColumn;
    @FXML
    private TableColumn<TableOrder, Integer> billColumn;
    @FXML
    private TableColumn<TableOrder, String> statusColumn;
    @FXML
    private TableColumn<TableOrder, Integer> tableIdColumn;
    @FXML
    private TableColumn<TableOrder, String> reservationColumn;

    @FXML
    private ComboBox<String> newStatusComboBox;

    @FXML
    private Button changeSortButton;

    private boolean sortByOrderNumber = true;


    @FXML
    private void initialize() {
        List<Table> tables = TableRepository.fetchTablesFromDatabasePublic();
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        billColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBill()).asObject());
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        tableIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTableId()).asObject());
        reservationColumn.setCellValueFactory(data -> {
            TableOrder tableOrder = data.getValue();
            Table table = tables.stream()
                    .filter(t -> t.number() == tableOrder.getTableId())
                    .findFirst()
                    .orElse(null);
            return new SimpleStringProperty(table != null ? table.reservationName() : "Aucune réservation");
        });
        onShowOrdersClick();
        updateSortMode();
    }
    @FXML
    private void onShowOrdersClick() {
        ordersTableView.getItems().clear();
        List<TableOrder> orders = TableOrderRepository.fetchOrders();
        ordersTableView.getItems().addAll(orders);
    }

    @FXML
    private void onDeleteOrderClick() {
        TableOrder selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            TableOrderRepository.deleteOrderById(selectedOrder.getId());
            ordersTableView.getItems().remove(selectedOrder);
        }
    }

    @FXML
    private void onModifyStatusClick() {
        TableOrder selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        String newStatus = newStatusComboBox.getValue();

        if (selectedOrder == null || newStatus == null || newStatus.isEmpty()) {
            System.err.println("Veuillez sélectionner une commande et un nouveau statut.");
            return;
        }

        try {
            TableOrderRepository.updateOrderStatus(selectedOrder.getId(), newStatus);
            onShowOrdersClick();
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }

    @FXML
    public void handleChangeSortMode() {
        sortByOrderNumber = !sortByOrderNumber;
        updateSortMode();
    }

    private void updateSortMode() {
        if (sortByOrderNumber) {

            ordersTableView.getSortOrder().clear();
            ordersTableView.getSortOrder().add(idColumn);
            idColumn.setSortType(TableColumn.SortType.ASCENDING);
        } else {
            ordersTableView.getSortOrder().clear();
            ordersTableView.getSortOrder().add(reservationColumn);
            reservationColumn.setSortType(TableColumn.SortType.ASCENDING);
        }
    }
}
