package com.forkfoe.forkfoe;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class TableOrderController {

    @FXML
    private ListView<String> ordersListView;
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
    private void onShowOrdersClick() {

        // Vider la ListView actuelle
        ordersListView.getItems().clear();

        List<TableOrder> orders = TableOrderRepository.fetchOrders();

        // Ajouter chaque commande directement dans la ListView
        for (TableOrder order : orders) {
            String orderInfo = "Commande #" + order.getId() +
                    " | Montant: " + order.getBill() +
                    " | Statut: " + order.getStatus() +
                    " | Table: " + order.getTableId();
            ordersListView.getItems().add(orderInfo);
        }
    }


    @FXML
    private void onCreateOrderClick() {
        try {
            int bill = Integer.parseInt(billField.getText());
            String status = statusComboBox.getValue();
            int tableId = Integer.parseInt(tableIdField.getText());

            List<TableOrder> orders = TableOrderRepository.getOrders();


            TableOrder newOrder = new TableOrder(bill, status, tableId);

            TableOrderRepository.addOrder(newOrder);

            billField.clear();
            statusComboBox.getSelectionModel().clearSelection();
            tableIdField.clear();
            onShowOrdersClick();

        } catch (NumberFormatException e) {
            System.err.println("Erreur : Veuillez entrer des valeurs valides.");
        }
    }

    @FXML
    private void onDeleteOrderClick() {
        // Récupérer la commande sélectionnée dans la ListView
        String selectedOrderInfo = ordersListView.getSelectionModel().getSelectedItem();
        if (selectedOrderInfo != null) {
            // Extraire l'ID de la commande à partir du texte de la commande (tu devras probablement adapter cela)
            int orderId = extractOrderId(selectedOrderInfo);

            // Supprimer la commande de la base de données
            TableOrderRepository.deleteOrderById(orderId);

            // Supprimer la commande de la ListView
            ordersListView.getItems().remove(selectedOrderInfo);
            //onShowOrdersClick();
        }
    }

    private int extractOrderId(String orderInfo) {
        String[] parts = orderInfo.split(" ");
        return Integer.parseInt(parts[1].substring(1)); // On suppose que l'ID est après "Commande #"
    }

    @FXML
    private void onModifyStatusClick() {
        String selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
        String newStatus = newStatusComboBox.getValue();

        if (selectedOrder == null || newStatus == null || newStatus.isEmpty()) {
            System.err.println("Veuillez sélectionner une commande et un nouveau statut.");
            return;
        }

        try {
            int orderId = Integer.parseInt(selectedOrder.split("#")[1].split(" ")[0]);

            // Mettre à jour le statut dans la base
            TableOrderRepository.updateOrderStatus(orderId, newStatus);

            // Rafraîchir la liste
            onShowOrdersClick();

        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }

}