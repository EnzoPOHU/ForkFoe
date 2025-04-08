package com.forkfoe.forkfoe;

import java.sql.*;

public final class Database {
    static { new Database(); }

    private static Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        execute("CREATE TABLE IF NOT EXISTS restaurantTable ("
                    + "	id INTEGER PRIMARY KEY,"
                    + "	seat INTEGER NOT NULL,"
                    + "	number INTEGER NOT NULL);");

        execute("CREATE TABLE IF NOT EXISTS client ("
                    + "	id INTEGER PRIMARY KEY,"
                    + "	table_number INTEGER NOT NULL);");

        execute( "CREATE TABLE IF NOT EXISTS staff ("
                    + "	id INTEGER PRIMARY KEY,"
                    + "	name VARCHAR NOT NULL,"
                    + " role VARCHAR NOT NULL);");

        execute("CREATE TABLE IF NOT EXISTS tableOrder ("
                    + "	id INTEGER PRIMARY KEY, "
                    + " bill INTEGER, "
                    + " status VARCHAR);");

        execute("CREATE TABLE IF NOT EXISTS tableDish ("
                    + "	id INTEGER PRIMARY KEY, "
                    + " name VARCHAR, "
                    + " description TEXT, "
                    + " price INTEGER, "
                    + " img BLOB);");

        execute("CREATE TABLE IF NOT EXISTS orderDish ("
                    + "	id INTEGER PRIMARY KEY, "
                    + " orderID INTEGER, "
                    + " dishID INTEGER, "
                    + " quantity INTEGER);");
    }

    public static void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("[SQLite] Error: " + e.getMessage());
        }
    }
}
