package database;

import utility.ConsolePrinter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String JDBC_DRIVER = "org.postgresql.Driver";
    private final String url;
    private final String username;
    private final String password;
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

    public Connection getConnection() {
        return connection;
    }
}
