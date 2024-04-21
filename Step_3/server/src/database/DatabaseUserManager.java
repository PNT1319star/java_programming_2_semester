package database;

import exceptions.HandlingDatabaseException;
import interaction.User;
import utility.ConsolePrinter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUserManager {
    private final DatabaseConnector databaseConnector;

    public DatabaseUserManager(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public User selectUserById(int userId) throws SQLException {
        User user;
        PreparedStatement preparedSelectedUserByIdStatement = null;
        try {
            preparedSelectedUserByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_BY_ID, false);
            preparedSelectedUserByIdStatement.setInt(1, userId);
            ResultSet resultSet = preparedSelectedUserByIdStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(DatabaseConstants.USER_TABLE_USERNAME_COLUMN),
                        resultSet.getString(DatabaseConstants.USER_TABLE_PASSWORD_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_USER_BY_ID query!");
            throw new SQLException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedUserByIdStatement);
        }
        return user;
    }

    public int selectUserIdByUsername(User user) throws HandlingDatabaseException {
        int userId;
        PreparedStatement preparedSelectedUserIdByUsernameStatement = null;
        try {
            preparedSelectedUserIdByUsernameStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_BY_USERNAME, false);
            preparedSelectedUserIdByUsernameStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedSelectedUserIdByUsernameStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(DatabaseConstants.USER_TABLE_ID_COLUMN);
            } else userId = -1;
            return userId;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_USER_BY_USERNAME query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedUserIdByUsernameStatement);
        }
    }

    public boolean checkUserByUsernameAndPassword(User user) throws HandlingDatabaseException {
        PreparedStatement preparedSelectedUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectedUserByUsernameAndPasswordStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedSelectedUserByUsernameAndPasswordStatement.setString(1, user.getUsername());
            preparedSelectedUserByUsernameAndPasswordStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedSelectedUserByUsernameAndPasswordStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_USER_BY_USERNAME_AND_PASSWORD query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedUserByUsernameAndPasswordStatement);
        }
    }
    public boolean insertUser(User user) throws HandlingDatabaseException {
        PreparedStatement preparedInsertedUserStatement = null;
        try {
            if (selectUserIdByUsername(user) != -1) return false;
            preparedInsertedUserStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_USER, false);
            preparedInsertedUserStatement.setString(1, user.getUsername());
            preparedInsertedUserStatement.setString(2, user.getPassword());
            if (preparedInsertedUserStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the INSERT_USER query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedInsertedUserStatement);
        }
    }
}
