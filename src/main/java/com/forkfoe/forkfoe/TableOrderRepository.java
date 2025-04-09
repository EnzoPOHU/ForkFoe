package com.forkfoe.forkfoe;

import java.util.*;
import java.util.stream.*;

public class TableOrderRepository {
    static { new TableOrderRepository(); }

    private static List<TableOrder> orders;

    private TableOrderRepository() {
        orders = fetchOrders();
    }

    public static List<TableOrder> getOrders() {
        return orders;
    }

    public static List<TableOrder> fetchOrders() {
        try {
            orders = SQLiteWrapper.execute("SELECT * FROM tableOrder").stream()
                    .map(row -> new TableOrder(
                            (Integer) row[0],
                            (Integer) row[1],              // bill
                            (String) row[2],               // status
                            (Integer) row[3]               // table_id
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Échec lors du chargement des commandes : " + e.getMessage());
        }
        return orders;
    }

    public static void addOrder(TableOrder order) {
        try {
            SQLiteWrapper.execute(
                    "INSERT INTO tableOrder (bill, status, table_id) VALUES (?, ?, ?)",
                    order.getBill(), order.getStatus(), order.getTableId()
            );
            orders.add(order);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'une commande : " + e.getMessage());
        }
    }

    public static void updateOrderStatus(int orderId, String newStatus) {
        try {
            SQLiteWrapper.execute("UPDATE tableOrder SET status = ? WHERE id = ?", newStatus, orderId);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du statut de la commande: " + e.getMessage());
        }
    }

    public static void deleteOrderById(int orderId) {
        try {
            System.out.println(orderId);
            SQLiteWrapper.execute("DELETE FROM tableOrder WHERE id = ?", orderId);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la commande: " + e.getMessage());
        }
    }

}
