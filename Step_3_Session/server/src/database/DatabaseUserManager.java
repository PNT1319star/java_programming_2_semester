package database;

import authentication.SessionIdCreator;
import exceptions.HandlingDatabaseException;
import exceptions.NotUpdateException;
import interaction.User;
import utilities.PasswordEncryptor;
import utility.ConsolePrinter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
                user = resultSet.getString(DatabaseConstants.USER_TABLE_USERNAME_COLUMN);
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

    public int insertUserIntoUserTable(User user) throws HandlingDatabaseException {
        PreparedStatement preparedInsertedUserStatement = null;
        try {
            if (selectUserIdByUsername(user.getUsername()) != -1) return -1;
            preparedInsertedUserStatement =
                    StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_USER, true);
            String username = user.getUsername();
            String hashedPassword = PasswordEncryptor.hashPassword(user.getPassword());
            if (username.equals("admin")) {
                preparedInsertedUserStatement.setString(1, username);
                preparedInsertedUserStatement.setString(2, hashedPassword);
                preparedInsertedUserStatement.setInt(3, 1);
            } else {
                preparedInsertedUserStatement.setString(1, username);
                preparedInsertedUserStatement.setString(2, hashedPassword);
                preparedInsertedUserStatement.setInt(3, 3);
            }
            preparedInsertedUserStatement.executeUpdate();
            ResultSet generatedKeys = preparedInsertedUserStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the INSERT_USER query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedInsertedUserStatement);
        }
    }

    public String insertUserIdIntoSessionsTable(int user_id) throws HandlingDatabaseException, NotUpdateException {
        PreparedStatement insertUserIdStatement = null;
        try {
            insertUserIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_NEW_USER_INTO_SESSION_TABLE, false);
            String sessionId = SessionIdCreator.createSessionId();
            LocalDateTime currentTime = LocalDateTime.now();
            Timestamp creationTime = Timestamp.valueOf(currentTime);
            LocalDateTime futureTime = LocalDateTime.now().plusHours(24);
            Timestamp expirationTime = Timestamp.valueOf(futureTime);
            insertUserIdStatement.setString(1, sessionId);
            insertUserIdStatement.setInt(2, user_id);
            insertUserIdStatement.setTimestamp(3, creationTime);
            insertUserIdStatement.setTimestamp(4, expirationTime);
            if (insertUserIdStatement.executeUpdate() > 0) {
                return sessionId;
            } else if (insertUserIdStatement.executeUpdate() == 0) {
                throw new NotUpdateException();
            } else throw new SQLException();
        } catch (SQLException e) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(insertUserIdStatement);
        }
    }

    public String getSessionIdByUserId(int user_id) throws HandlingDatabaseException {
        PreparedStatement selectSessionIdStatement = null;
        try {
            selectSessionIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_SESSION_ID_BY_USER_ID, false);
            selectSessionIdStatement.setInt(1, user_id);
            ResultSet resultSet = selectSessionIdStatement.executeQuery();
            if (resultSet.next()) return resultSet.getString("id");
            else throw new SQLException();
        } catch (SQLException e) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(selectSessionIdStatement);
        }
    }

    public boolean updateUserRole(String username, String role) throws HandlingDatabaseException {
        PreparedStatement updateUserRoleStatement = null;
        try {
            PreparedStatement getRoleIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ROLE_ID_BY_ROLE, false);
            getRoleIdStatement.setString(1, role);
            ResultSet resultSet = getRoleIdStatement.executeQuery();
            if (!resultSet.next()) throw new HandlingDatabaseException();
            int roleId = resultSet.getInt("id");
            StatementBuilder.closedPreparedStatement(getRoleIdStatement);
            updateUserRoleStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ROLE_ID_BY_USERNAME, false);
            updateUserRoleStatement.setInt(1, roleId);
            updateUserRoleStatement.setString(2, username);
            int rowsUpdate = updateUserRoleStatement.executeUpdate();
            return rowsUpdate > 0;
        } catch (SQLException exception) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(updateUserRoleStatement);
        }
    }

}
