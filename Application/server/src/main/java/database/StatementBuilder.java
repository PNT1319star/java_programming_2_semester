package database;

import org.csjchoisoojong.utility.ConsolePrinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementBuilder {
    public static PreparedStatement buildPreparedStatement(Connection connection, String sqlStatement, boolean generateKeys) throws SQLException {
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS;
            return connection.prepareStatement(sqlStatement, autoGeneratedKeys);
        } catch (SQLException exception) {
            throw new SQLException(exception);
        }
    }
    public static void closedPreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while closing the SQL query!");
        }
    }
}
