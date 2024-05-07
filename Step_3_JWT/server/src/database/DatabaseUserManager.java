package database;

import exceptions.HandlingDatabaseException;
import interaction.User;
import utilities.PasswordEncryptor;
import utility.ConsolePrinter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUserManager {
    private final DatabaseConnector databaseConnector;

    public DatabaseUserManager(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public String selectUserById(int userId) throws SQLException {
        String user;
        PreparedStatement preparedSelectedUserByIdStatement = null;
        try {
            preparedSelectedUserByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_BY_ID, false);
            preparedSelectedUserByIdStatement.setInt(1, userId);
            ResultSet resultSet = preparedSelectedUserByIdStatement.executeQuery();
            if (resultSet.next()) {
                user =  resultSet.getString(DatabaseConstants.USER_TABLE_USERNAME_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_USER_BY_ID query!");
            throw new SQLException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedUserByIdStatement);
        }
        return user;
    }

    public int selectUserIdByUsername(String userName) throws HandlingDatabaseException {
        int userId;
        PreparedStatement preparedSelectedUserIdByUsernameStatement = null;
        try {
            preparedSelectedUserIdByUsernameStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_BY_USERNAME, false);
            preparedSelectedUserIdByUsernameStatement.setString(1, userName);
            ResultSet resultSet = preparedSelectedUserIdByUsernameStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(DatabaseConstants.USER_TABLE_ID_COLUMN);
            } else userId = -1;
            return userId;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_USER_BY_USERNAME query!");
            exception.printStackTrace();
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedUserIdByUsernameStatement);
        }
    }

    public boolean checkUserByUsernameAndPassword(User user) throws HandlingDatabaseException {
        PreparedStatement preparedSelectedUserByUsernameAndPasswordStatement = null;
        String username = user.getUsername();
        String hashedPassword = PasswordEncryptor.hashPassword(user.getPassword());
        try {
            preparedSelectedUserByUsernameAndPasswordStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedSelectedUserByUsernameAndPasswordStatement.setString(1, username);
            preparedSelectedUserByUsernameAndPasswordStatement.setString(2, hashedPassword);
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
            if (selectUserIdByUsername(user.getUsername()) != -1) return false;
            preparedInsertedUserStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_USER, false);
            String username = user.getUsername();
            String hashedPassword = PasswordEncryptor.hashPassword(user.getPassword());
            preparedInsertedUserStatement.setString(1, username);
            preparedInsertedUserStatement.setString(2, hashedPassword);
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
