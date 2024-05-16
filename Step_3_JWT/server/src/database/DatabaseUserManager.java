package database;

import exceptions.HandlingDatabaseException;
import interaction.User;
import utilities.PasswordEncryptor;
import utilities.Roles;
import utility.ConsolePrinter;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            int rowsUpdate = preparedInsertedUserStatement.executeUpdate();
            return rowsUpdate > 0;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the INSERT_USER query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedInsertedUserStatement);
        }
    }

    public Roles getUserRole(User user) throws HandlingDatabaseException {
        PreparedStatement getRoleIdStatement = null;
        String username = user.getUsername();
        Roles userRole;
        try {
            getRoleIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ROLE_ID_BY_USERNAME, false);
            getRoleIdStatement.setString(1, username);
            ResultSet resultSet = getRoleIdStatement.executeQuery();
            if (resultSet.next()) {
                int roleID = resultSet.getInt(DatabaseConstants.USER_TABLE_ROLE_ID_COLUMN);
                PreparedStatement getRoleStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ROLE_BY_ROLE_ID, false);
                getRoleStatement.setInt(1, roleID);
                ResultSet resultSet1 = getRoleStatement.executeQuery();
                if (resultSet1.next()) {
                    userRole = Roles.valueOf(resultSet1.getString("id"));
                } else throw new SQLException();
                getRoleStatement.close();
            } else throw new SQLException();
            return userRole;
        } catch (SQLException exception) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(getRoleIdStatement);
        }
    }

    public List<String> getFunctionList(Roles role) throws HandlingDatabaseException {
        List<String> functionList = new ArrayList<>();
        PreparedStatement getFunctionStatement = null;
        try {
            getFunctionStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_FUNCTION_BY_ROLE, false);
            getFunctionStatement.setString(1, role.toString().toLowerCase());
            ResultSet resultSet = getFunctionStatement.executeQuery();
            while (resultSet.next()) {
                String function = resultSet.getString("function");
                functionList.add(function);
            }
        } catch (SQLException exception) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(getFunctionStatement);
        }
        return functionList;
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
