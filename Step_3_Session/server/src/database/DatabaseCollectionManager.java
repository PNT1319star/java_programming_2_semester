package database;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import exceptions.HandlingDatabaseException;
import interaction.OrganizationRaw;
import utility.ConsolePrinter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;

public class DatabaseCollectionManager {
    private final DatabaseConnector databaseConnector;
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseHandler databaseHandler;


    public DatabaseCollectionManager(DatabaseConnector databaseConnector, DatabaseUserManager databaseUserManager, DatabaseHandler databaseHandler) {
        this.databaseConnector = databaseConnector;
        this.databaseUserManager = databaseUserManager;
        this.databaseHandler = databaseHandler;
    }

    public int getUserIdByUsername(String username) throws HandlingDatabaseException {
        return databaseUserManager.selectUserIdByUsername(username);
    }

    // add command, add_if_max command
    public boolean insertOrganization(OrganizationRaw rawOrganization, int userId) throws HandlingDatabaseException {
        PreparedStatement preparedInsertOrganizationStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertAddressStatement = null;
        try {
            databaseHandler.startTransaction();
            databaseHandler.setSavepoint();
            ZonedDateTime creationDate = ZonedDateTime.now();
            preparedInsertOrganizationStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_ORGANIZATION, true);
            preparedInsertCoordinatesStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_COORDINATES, true);
            preparedInsertAddressStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_ADDRESS, true);
            preparedInsertAddressStatement.setString(1, rawOrganization.getAddress().getStreet());

            // if this address has been existed in the table ADDRESS
            int addressId = 0;
            addressId = getAddressId(rawOrganization, preparedInsertAddressStatement, addressId);
            // if these coordinates have been existed in the table COORDINATES
            int coordinatesId = 0;
            coordinatesId = getCoordinatesId(rawOrganization, preparedInsertCoordinatesStatement, coordinatesId);
            preparedInsertOrganizationStatement.setString(1, rawOrganization.getName());
            preparedInsertOrganizationStatement.setTimestamp(2, Timestamp.valueOf(creationDate.toLocalDateTime()));
            preparedInsertOrganizationStatement.setFloat(3, rawOrganization.getAnnualTurnover());
            preparedInsertOrganizationStatement.setString(4, rawOrganization.getFullName());
            preparedInsertOrganizationStatement.setInt(5, coordinatesId);
            preparedInsertOrganizationStatement.setInt(6, rawOrganization.getEmployeesCount());
            preparedInsertOrganizationStatement.setString(7, rawOrganization.getType().toString());
            preparedInsertOrganizationStatement.setInt(8, addressId);
            preparedInsertOrganizationStatement.setInt(9, userId);
            int rowsUpdate = preparedInsertOrganizationStatement.executeUpdate();
            databaseHandler.commitTransaction();
            return rowsUpdate > 0;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the INSERT_ORGANIZATION query!");
            exception.printStackTrace();
            databaseHandler.rollbackTransaction();
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedInsertOrganizationStatement);
            StatementBuilder.closedPreparedStatement(preparedInsertCoordinatesStatement);
            StatementBuilder.closedPreparedStatement(preparedInsertAddressStatement);
            databaseHandler.stopTransaction();
        }
    }

    private int getAddressId(OrganizationRaw rawOrganization, PreparedStatement preparedInsertAddressStatement, int addressId) throws SQLException {
        if (preparedInsertAddressStatement.executeUpdate() == 0) {
            PreparedStatement selectAddressStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ADDRESS_ID_BY_ADDRESS, false);
            selectAddressStatement.setString(1, rawOrganization.getAddress().getStreet());
            ResultSet resultSet = selectAddressStatement.executeQuery();
            if (resultSet.next()) {
                addressId = resultSet.getInt("id");
            }
            selectAddressStatement.close();
        } else {
            ResultSet generatedAddressKeys = preparedInsertAddressStatement.getGeneratedKeys();
            if (generatedAddressKeys.next()) {
                addressId = generatedAddressKeys.getInt(1);
            } else throw new SQLException();
        }
        return addressId;
    }

    private int getCoordinatesId(OrganizationRaw rawOrganization, PreparedStatement preparedInsertCoordinatesStatement, int coordinatesId) throws SQLException {
        preparedInsertCoordinatesStatement.setLong(1, rawOrganization.getCoordinates().getX());
        preparedInsertCoordinatesStatement.setLong(2, rawOrganization.getCoordinates().getY());
        if (preparedInsertCoordinatesStatement.executeUpdate() == 0) {
            PreparedStatement selectCoordinateStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_COORDINATE_ID_BY_X_AND_Y, false);
            selectCoordinateStatement.setLong(1, rawOrganization.getCoordinates().getX());
            selectCoordinateStatement.setLong(2, rawOrganization.getCoordinates().getY());
            ResultSet resultSet = selectCoordinateStatement.executeQuery();
            if (resultSet.next()) {
                coordinatesId = resultSet.getInt(DatabaseConstants.COORDINATES_TABLE_ID_COLUMN);
            }
            selectCoordinateStatement.close();
        } else {
            ResultSet generatedCoordinatesKeys = preparedInsertCoordinatesStatement.getGeneratedKeys();
            if (generatedCoordinatesKeys.next()) {
                coordinatesId = generatedCoordinatesKeys.getInt(1);
            } else throw new SQLException();
        }
        return coordinatesId;
    }


    public boolean deleteOrganizationByUsername(int userId) throws HandlingDatabaseException {
        PreparedStatement preparedDeleteOrganizationByUsernameStatement = null;
        try {
            preparedDeleteOrganizationByUsernameStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.DELETE_ORGANIZATION_BY_USER_ID, false);
            preparedDeleteOrganizationByUsernameStatement.setInt(1, userId);
            int affectedRows = preparedDeleteOrganizationByUsernameStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the DELETE_ORGANIZATION_BY_USER_ID query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedDeleteOrganizationByUsernameStatement);
        }
    }

    // remove_by_id command
    public boolean deleteOrganizationById(int organizationId, int userId) throws HandlingDatabaseException {
        PreparedStatement preparedDeleteOrganizationByIdStatement = null;
        try {
            preparedDeleteOrganizationByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.DELETE_ORGANIZATION_BY_ID_AND_USER_ID, false);
            preparedDeleteOrganizationByIdStatement.setInt(1, organizationId);
            preparedDeleteOrganizationByIdStatement.setInt(2, userId);
            int affectedRows = preparedDeleteOrganizationByIdStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the DELETE_ORGANIZATION_BY_ID request!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedDeleteOrganizationByIdStatement);
        }
    }


    // update command
    public boolean updateOrganizationById(int organizationId, OrganizationRaw organizationRaw) throws
            HandlingDatabaseException {
        PreparedStatement preparedUpdateOrganizationNameByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationTimeStatement = null;
        PreparedStatement preparedUpdateOrganizationTurnoverByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationFullNameByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationEmployeesCountByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationTypeByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByOrganizationIdStatement = null;
        PreparedStatement preparedUpdateAddressByIdStatement = null;
        int count = 0;
        try {
            databaseHandler.startTransaction();
            databaseHandler.setSavepoint();
            ZonedDateTime creationDate = ZonedDateTime.now();

            preparedUpdateOrganizationNameByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_NAME_BY_ID, false);
            preparedUpdateOrganizationTimeStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_TIME_BY_ID, false);
            preparedUpdateOrganizationTurnoverByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_TURNOVER_BY_ID, false);
            preparedUpdateOrganizationFullNameByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_FULL_NAME_BY_ID, false);
            preparedUpdateCoordinatesByOrganizationIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_COORDINATES, false);
            preparedUpdateOrganizationEmployeesCountByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_EMPLOYEE_BY_ID, false);
            preparedUpdateOrganizationTypeByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_TYPE_BY_ID, false);
            preparedUpdateAddressByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_ADDRESS_BY_ID, false);

            if (organizationRaw.getName() != null) {
                preparedUpdateOrganizationNameByIdStatement.setString(1, organizationRaw.getName());
                preparedUpdateOrganizationNameByIdStatement.setInt(2, organizationId);
                count += preparedUpdateOrganizationNameByIdStatement.executeUpdate();
            }
            preparedUpdateOrganizationTimeStatement.setTimestamp(1, Timestamp.valueOf(creationDate.toLocalDateTime()));
            preparedUpdateOrganizationTimeStatement.setInt(2, organizationId);
            count += preparedUpdateOrganizationTimeStatement.executeUpdate();
            if (organizationRaw.getAnnualTurnover() != null) {
                preparedUpdateOrganizationTurnoverByIdStatement.setFloat(1, organizationRaw.getAnnualTurnover());
                preparedUpdateOrganizationTurnoverByIdStatement.setInt(2, organizationId);
                count += preparedUpdateOrganizationTurnoverByIdStatement.executeUpdate();
            }
            if (organizationRaw.getFullName() != null) {
                preparedUpdateOrganizationFullNameByIdStatement.setString(1, organizationRaw.getFullName());
                preparedUpdateOrganizationFullNameByIdStatement.setInt(2, organizationId);
                count += preparedUpdateOrganizationFullNameByIdStatement.executeUpdate();
            }
            if (organizationRaw.getCoordinates() != null) {
                int coordinatesId = 0;
                PreparedStatement insertCoordinatesStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_COORDINATES, true);
                coordinatesId = getCoordinatesId(organizationRaw, insertCoordinatesStatement, coordinatesId);
                preparedUpdateCoordinatesByOrganizationIdStatement.setInt(1, coordinatesId);
                preparedUpdateCoordinatesByOrganizationIdStatement.setInt(2, organizationId);
                count += preparedUpdateCoordinatesByOrganizationIdStatement.executeUpdate();
            }
            if (organizationRaw.getEmployeesCount() != null) {
                preparedUpdateOrganizationEmployeesCountByIdStatement.setInt(1, organizationRaw.getEmployeesCount());
                preparedUpdateOrganizationEmployeesCountByIdStatement.setInt(2, organizationId);
                count += preparedUpdateOrganizationEmployeesCountByIdStatement.executeUpdate();
            }
            if (organizationRaw.getType() != null) {
                preparedUpdateOrganizationTypeByIdStatement.setString(1, organizationRaw.getType().toString());
                preparedUpdateOrganizationTypeByIdStatement.setInt(2, organizationId);
                count += preparedUpdateOrganizationTypeByIdStatement.executeUpdate();
            }
            if (organizationRaw.getAddress() != null) {
                int addressId = 0;
                PreparedStatement insertAddressStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.INSERT_ADDRESS, true);
                insertAddressStatement.setString(1, organizationRaw.getAddress().getStreet());
                addressId = getAddressId(organizationRaw, insertAddressStatement, addressId);
                preparedUpdateAddressByIdStatement.setInt(1, addressId);
                preparedUpdateAddressByIdStatement.setInt(2, organizationId);
                count += preparedUpdateAddressByIdStatement.executeUpdate();
            }
            databaseHandler.commitTransaction();
            return count >= 0;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing a group of requests to update an object!");
            exception.printStackTrace();
            databaseHandler.rollbackTransaction();
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationNameByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationTimeStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationTurnoverByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationFullNameByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationEmployeesCountByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationTypeByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateCoordinatesByOrganizationIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateAddressByIdStatement);
            databaseHandler.stopTransaction();
        }
    }

    //Load collection
    public ArrayDeque<Organization> loadCollection() throws HandlingDatabaseException {
        ArrayDeque<Organization> organizationArrayDeque = new ArrayDeque<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ALL_ORGANIZATIONS, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                organizationArrayDeque.add(createOrganization(resultSet));
            }
        } catch (SQLException exception) {
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectAllStatement);
        }
        return organizationArrayDeque;
    }

    private Coordinates getCoordinatesByCoordinateId(int coordinateId) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedSelectedCoordinatesByOrganizationIdStatement = null;
        try {
            preparedSelectedCoordinatesByOrganizationIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_COORDINATES_BY_COORDINATE_ID, false);
            preparedSelectedCoordinatesByOrganizationIdStatement.setInt(1, coordinateId);
            ResultSet resultSet = preparedSelectedCoordinatesByOrganizationIdStatement.executeQuery();
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getLong(DatabaseConstants.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getLong(DatabaseConstants.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_COORDINATES_BY_ORGANIZATION_ID query!");
            throw new SQLException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedCoordinatesByOrganizationIdStatement);
        }
        return coordinates;
    }

    private Address getAddressById(int addressId) throws SQLException {
        Address address;
        PreparedStatement preparedGetAddressByIdStatement = null;
        try {
            preparedGetAddressByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ADDRESS_BY_ID, false);
            preparedGetAddressByIdStatement.setInt(1, addressId);
            ResultSet resultSet = preparedGetAddressByIdStatement.executeQuery();
            if (resultSet.next()) {
                address = new Address(resultSet.getString(DatabaseConstants.ADDRESS_TABLE_STREET_COLUMN));
            } else throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_ADDRESS_BY_ID query!");
            throw new SQLException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedGetAddressByIdStatement);
        }
        return address;
    }

    private Organization createOrganization(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_ID_COLUMN);
        String name = resultSet.getString(DatabaseConstants.ORGANIZATION_TABLE_NAME_COLUMN);
        ZonedDateTime creationDate = resultSet.getTimestamp(DatabaseConstants.ORGANIZATION_TABLE_TIME_COLUMN).toLocalDateTime().atZone(ZoneId.systemDefault());
        Coordinates coordinates = getCoordinatesByCoordinateId(resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_COORDINATES_ID));
        Float annualTurnover = resultSet.getFloat(DatabaseConstants.ORGANIZATION_TABLE_TURNOVER_COLUMN);
        String fullName = resultSet.getString(DatabaseConstants.ORGANIZATION_TABLE_FULL_NAME_COLUMN);
        Integer employeesCount = resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_EMPLOYEE_COLUMN);
        OrganizationType type = OrganizationType.valueOf(resultSet.getString(DatabaseConstants.ORGANIZATION_TABLE_TYPE_COLUMN));
        Address address = getAddressById(resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_ADDRESS_ID_COLUMN));
        String owner = databaseUserManager.selectUserById(resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_USER_ID_COLUMN));
        return new Organization(id, name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, address, owner);
    }

}
