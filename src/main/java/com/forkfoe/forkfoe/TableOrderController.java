package com.forkfoe.forkfoe;

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
    private TextField billField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextField tableIdField;
    @FXML
    private ComboBox<String> newStatusComboBox;

    public Button createOrderButton;
    public Button showOrdersButton;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        billColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBill()).asObject());
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        tableIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTableId()).asObject());
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
}
