package com.forkfoe.forkfoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static Database instance;
    public Connection connection;

    private Database() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            if (connection != null) {
                System.out.println("[SQLite] Successfully connected to database") ;
                this.connection = connection;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
