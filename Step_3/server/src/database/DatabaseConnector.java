package database;

import utility.ConsolePrinter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String JDBC_DRIVER = "org.postgreSQL.Driver";
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connectToDatabase() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, username, password);
            ConsolePrinter.printResult("Database connection established!");
        } catch (ClassNotFoundException e) {
            ConsolePrinter.printError("Database management driver not found!");
        } catch (SQLException e) {
            ConsolePrinter.printError("An error occurred while connecting to the database!");
        }
    }

    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            ConsolePrinter.printResult("The connection to the database was lost.");
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while disconnecting the database connection!");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
