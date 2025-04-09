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


    public Button createOrderButton;
    public Button showOrdersButton;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Fork Foe!");
    }

    @FXML
    private void onShowOrdersClick() {

        // Vider la ListView actuelle
        ordersListView.getItems().clear();

        List<TableOrder> orders = TableOrderRepository.fetchOrders();

        System.out.println(orders);
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

            int maxId = 0;
            for (TableOrder order : orders) {
                if (order.getId() > maxId) {
                    maxId = order.getId();
                }
            }

            int newOrderId = maxId + 1;

            TableOrder newOrder = new TableOrder(newOrderId, bill, status, tableId);

            TableOrderRepository.addOrder(newOrder);

            System.out.println("Commande créée avec succès !");

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
        // Extraire l'ID de la commande à partir de la chaîne de texte
        // Par exemple : "Commande #1 | Montant: 50 | Statut: En attente | Table: 3"
        String[] parts = orderInfo.split(" ");
        return Integer.parseInt(parts[1].substring(1)); // On suppose que l'ID est après "Commande #"
    }

}