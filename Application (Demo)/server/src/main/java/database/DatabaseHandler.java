package database;

import org.csjchoisoojong.utility.ConsolePrinter;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseHandler {
    private final Connection connection;

    public DatabaseHandler(DatabaseConnector databaseConnector) {
        this.connection = databaseConnector.getConnection();
    }

    public void startTransaction() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while setting the database transaction mode!");
        }
    }
    public void stopTransaction() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while setting the database to normal mode!");
        }
    }

    public void commitTransaction() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while confirming the new database state!");
        }
    }

    public void rollbackTransaction() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while restoring the database!");
        }
    }

    public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while saving the database state!");
        }
    }
}
