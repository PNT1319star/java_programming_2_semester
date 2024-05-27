package database;

import org.csjchoisoojong.exceptions.HandlingDatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSessionManager {
    private final DatabaseConnector databaseConnector;

    public DatabaseSessionManager(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }
    public List<String> getFunctionsList(String session_id) throws HandlingDatabaseException {
        PreparedStatement getFunctionStatement = null;
        List<String> functionsList = new ArrayList<>();
        try {
            getFunctionStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_FUNCTIONS_BY_SESSION_ID, false);
            getFunctionStatement.setString(1, session_id);
            ResultSet resultSet = getFunctionStatement.executeQuery();
            while (resultSet.next()) {
                functionsList.add(resultSet.getString("function"));
            }
            return functionsList;
        } catch (SQLException e) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(getFunctionStatement);
        }
    }

    public int getUserIdBySessionId(String session_id) throws HandlingDatabaseException {
        PreparedStatement getUserIdStatement = null;
        int user_id;
        try {
            getUserIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_ID_BY_SESSION_ID, false);
            getUserIdStatement.setString(1, session_id);
            ResultSet resultSet = getUserIdStatement.executeQuery();
            if (resultSet.next()) {
                user_id = resultSet.getInt("user_id");
            } else throw new SQLException();
            return user_id;
        } catch (SQLException e) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(getUserIdStatement);
        }
    }
}
