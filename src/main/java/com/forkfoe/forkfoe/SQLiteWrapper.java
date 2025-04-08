package com.forkfoe.forkfoe;

import java.sql.*;
import java.util.*;

/**
 * Singleton that wraps Java SQLite features to facilitate CRUD implementations
 */
public final class SQLiteWrapper {
    static { new SQLiteWrapper(); }

    private static Connection connection;

    private SQLiteWrapper() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Executes a batch of result less SQLite queries.
     * @param queries
     */
    public static void executeBatch(String []queries) {
       Arrays.stream(queries).toList().forEach(SQLiteWrapper::execute);
    }

    /**
     * Executes a SQLite query and return its result.
     * @param query
     * @return List of resulting rows from the query
     */
    public static List<Object[]> execute(String query) {
        List<Object[]> results = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; ++i) {
                    int columnIndex = i + 1;
                    switch (metaData.getColumnType(i+1)) {
                        case java.sql.Types.INTEGER: row[i] = resultSet.getInt(columnIndex); break;
                        case java.sql.Types.VARCHAR, java.sql.Types.CHAR: row[i] = resultSet.getString(columnIndex); break;
                        case java.sql.Types.DOUBLE: row[i] = resultSet.getDouble(columnIndex); break;
                        case java.sql.Types.BOOLEAN: row[i] = resultSet.getBoolean(columnIndex); break;
                        case java.sql.Types.FLOAT: row[i] = resultSet.getFloat(columnIndex); break;
                        case java.sql.Types.BIGINT: row[i] = resultSet.getLong(columnIndex); break;
                        case java.sql.Types.SMALLINT: row[i] = resultSet.getShort(columnIndex); break;
                        case java.sql.Types.TINYINT: row[i] = resultSet.getByte(columnIndex); break;
                        default: row[i] = resultSet.getObject(columnIndex); break;
                    }
                }
                results.add(row);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return results;
    }

    /**
     * Executes a prepared SQLite query consuming arguments.
     * @param preparedQuery
     * @param arguments Injected arguments in the prepared query
     */
    public static void execute(String preparedQuery, Object... arguments) {
        try (PreparedStatement statement = connection.prepareStatement(preparedQuery)) {
            for (int i = 0; i < arguments.length; ++i) {
                Object argument = arguments[i];
                int columnIndex = i + 1;
                switch (argument) {
                    case Byte byteArgument: statement.setByte(columnIndex, byteArgument); break;
                    case Boolean booleanArgument: statement.setBoolean(columnIndex, booleanArgument); break;
                    case Short shortArgument: statement.setShort(columnIndex, shortArgument); break;
                    case Float floatArgument: statement.setFloat(columnIndex, floatArgument); break;
                    case Integer integerArgument: statement.setInt(columnIndex, integerArgument); break;
                    case Long longArgument: statement.setLong(columnIndex, longArgument); break;
                    case Double doubleArgument: statement.setDouble(columnIndex, doubleArgument); break;
                    case String stringArgument: statement.setString(columnIndex, stringArgument); break;
                    default: statement.setObject(columnIndex, argument); break;
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
