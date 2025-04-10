package com.forkfoe.forkfoe.repository;

import com.forkfoe.forkfoe.SQLiteWrapper;
import com.forkfoe.forkfoe.model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableRepository {
    private static List<Table> tables = new ArrayList<>();

    static {
        fetchTablesFromDatabase();
    }

    private static void fetchTablesFromDatabase() {
        try {
            List<Object[]> rows = SQLiteWrapper.execute("SELECT number, seat, reservationName FROM restaurantTable");
            tables = rows.stream()
                    .map(row -> new Table(
                            (Integer) row[0],
                            (Integer) row[1],
                            row[2] != null ? (String) row[2] : "Aucune réservation"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des tables depuis la base de données : " + e.getMessage());
        }
    }

    public static List<Table> getTables() {
        return tables;
    }

    public static void addTable(Table table) {
        try {
            SQLiteWrapper.execute(
                    "INSERT INTO restaurantTable (number, seat, reservationName) VALUES (?, ?, ?)",
                    table.getNumber(), table.getMaxSeats(), table.getReservationName()
            );
            tables.add(table);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de la table : " + e.getMessage());
        }
    }


    public static void removeTable(Table table) {
        try {
            SQLiteWrapper.execute("DELETE FROM restaurantTable WHERE number = ?", table.getNumber());
            fetchTablesFromDatabase();
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la table : " + e.getMessage());
        }
    }


    public static void updateTable(Table updatedTable, int originalTableNumber) {
        try {
            SQLiteWrapper.execute(
                    "UPDATE restaurantTable SET number = ?, seat = ?, reservationName = ? WHERE number = ?",
                    updatedTable.getNumber(), updatedTable.getMaxSeats(), updatedTable.getReservationName(), originalTableNumber
            );
            tables = tables.stream()
                    .map(existing -> existing.getNumber() == originalTableNumber ? updatedTable : existing)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de la table : " + e.getMessage());
        }
    }
}