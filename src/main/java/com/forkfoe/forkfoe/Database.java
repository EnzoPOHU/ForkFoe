package com.forkfoe.forkfoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
    private static Database instance;
    public Connection connection;

    private Database() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            if (connection != null) {
                System.out.println("[SQLite] Successfully connected to database") ;
                this.connection = connection;

                var restaurantTable = "CREATE TABLE IF NOT EXISTS restaurantTable ("
                        + "	id INTEGER PRIMARY KEY,"
                        + "	seat INTEGER NOT NULL,"
                        + "	number INTEGER NOT NULL"
                        + ");";

                var client = "CREATE TABLE IF NOT EXISTS client ("
                        + "	id INTEGER PRIMARY KEY,"
                        + "	table_number INTEGER NOT NULL" //cléf étrangère vers number /table
                        + ");";

                var staff = "CREATE TABLE IF NOT EXISTS staff ("
                        + "	id INTEGER PRIMARY KEY,"
                        + "	name VARCHAR NOT NULL,"
                        + " role VARCHAR NOT NULL"
                        + ");";

                var tableOrder = "CREATE TABLE IF NOT EXISTS tableOrder ("
                        + "	id INTEGER PRIMARY KEY, "
                        + " bill INTEGER, "
                        + " status VARCHAR"
                        + ");";

                var tableDish = "CREATE TABLE IF NOT EXISTS tableDish ("
                        + "	id INTEGER PRIMARY KEY, "
                        + " name VARCHAR, "
                        + " description TEXT, "
                        + " price INTEGER, "
                        + " img BLOB"
                        + ");";

                var orderDish = "CREATE TABLE IF NOT EXISTS orderDish ("
                        + "	id INTEGER PRIMARY KEY, "
                        + " orderID INTEGER, "
                        + " dishID INTEGER, "
                        + " quantity INTEGER "
                        + ");";

                try (Statement stmt = connection.createStatement()) {
                    // Create new tables
                    stmt.execute(restaurantTable);
                    stmt.execute(client);
                    stmt.execute(staff);
                    stmt.execute(tableOrder);
                    stmt.execute(tableDish);
                    stmt.execute(orderDish);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to create tables: " + e.getMessage());
                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            System.out.println("test");
            instance = new Database();
        }
        System.out.println("test 2");
        return instance;
    }
}
