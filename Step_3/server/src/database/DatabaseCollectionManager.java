package database;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import exceptions.HandlingDatabaseException;
import interaction.OrganizationRaw;
import interaction.User;
import utility.ConsolePrinter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DatabaseCollectionManager {
    private final DatabaseConnector databaseConnector;
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseHandler databaseHandler;


    public DatabaseCollectionManager(DatabaseConnector databaseConnector, DatabaseUserManager databaseUserManager, DatabaseHandler databaseHandler) {
        this.databaseConnector = databaseConnector;
        this.databaseUserManager = databaseUserManager;
        this.databaseHandler = databaseHandler;
    }

    private Coordinates getCoordinatesByOrganizationId(int organizationId) throws SQLException {
        Coordinates coordinates ;
        PreparedStatement preparedSelectedCoordinatesByOrganizationIdStatement = null;
        try {
            preparedSelectedCoordinatesByOrganizationIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_COORDINATES_BY_ORGANIZATION_ID, false);
            preparedSelectedCoordinatesByOrganizationIdStatement.setInt(1, organizationId);
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

    private int getAddressIdById(int organizationId) throws SQLException {
        int addressId;
        PreparedStatement preparedSelectedAddressIdStatement = null;
        try {
            preparedSelectedAddressIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ORGANIZATION_BY_ID, false);
            preparedSelectedAddressIdStatement.setInt(1, organizationId);
            ResultSet resultSet = preparedSelectedAddressIdStatement.executeQuery();
            if (resultSet.next()) {
                addressId = resultSet.getInt(DatabaseConstants.ADDRESS_TABLE_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_ADDRESS_ID_BY_ORGANIZATION_ID query!");
            throw new SQLException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectedAddressIdStatement);
        }
        return addressId;
    }

    private Address getAddressById(int addressId) throws SQLException {
        Address address ;
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
        Coordinates coordinates = getCoordinatesByOrganizationId(id);
        Float annualTurnover = resultSet.getFloat(DatabaseConstants.ORGANIZATION_TABLE_TURNOVER_COLUMN);
        String fullName = resultSet.getString(DatabaseConstants.ORGANIZATION_TABLE_FULL_NAME_COLUMN);
        Integer employeesCount = resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_EMPLOYEE_COLUMN);
        OrganizationType type = OrganizationType.valueOf(resultSet.getString(DatabaseConstants.ORGANIZATION_TABLE_TYPE_COLUMN));
        Address address = getAddressById(resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_ADDRESS_ID_COLUMN));
        User owner = databaseUserManager.selectUserById(resultSet.getInt(DatabaseConstants.ORGANIZATION_TABLE_USER_ID_COLUMN));
        return new Organization(id, name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, address, owner);
    }

    public Organization insertOrganization(OrganizationRaw rawOrganization, User user) throws HandlingDatabaseException {
        Organization organization;
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
            if (preparedInsertAddressStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedAddressKeys = preparedInsertAddressStatement.getGeneratedKeys();
            int addressId;
            if (generatedAddressKeys.next()) {
                addressId = generatedAddressKeys.getInt(1);
            } else throw new SQLException();
            preparedInsertOrganizationStatement.setString(1, rawOrganization.getName());
            preparedInsertOrganizationStatement.setTimestamp(2, Timestamp.valueOf(creationDate.toLocalDateTime()));
            preparedInsertOrganizationStatement.setFloat(3, rawOrganization.getAnnualTurnover());
            preparedInsertOrganizationStatement.setString(4, rawOrganization.getFullName());
            preparedInsertOrganizationStatement.setInt(5, rawOrganization.getEmployeesCount());
            preparedInsertOrganizationStatement.setString(6, rawOrganization.getType().toString());
            preparedInsertOrganizationStatement.setInt(7, addressId);
            preparedInsertOrganizationStatement.setInt(8, databaseUserManager.selectUserIdByUsername(user));
            if (preparedInsertOrganizationStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedOrganizationKeys = preparedInsertOrganizationStatement.getGeneratedKeys();
            int organizationId;
            if (generatedOrganizationKeys.next()) {
                organizationId = generatedOrganizationKeys.getInt(1);
            } else throw new SQLException();
            preparedInsertCoordinatesStatement.setInt(1, organizationId);
            preparedInsertCoordinatesStatement.setLong(2, rawOrganization.getCoordinates().getX());
            preparedInsertCoordinatesStatement.setLong(3, rawOrganization.getCoordinates().getY());
            if (preparedInsertCoordinatesStatement.executeUpdate() == 0) throw new SQLException();
            organization = new Organization(
                    organizationId,
                    rawOrganization.getName(),
                    rawOrganization.getCoordinates(),
                    creationDate,
                    rawOrganization.getAnnualTurnover(),
                    rawOrganization.getFullName(),
                    rawOrganization.getEmployeesCount(),
                    rawOrganization.getType(),
                    rawOrganization.getAddress(),
                    user
            );
            databaseHandler.commitTransaction();
            return organization;
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the INSERT_ORGANIZATION query!");
            databaseHandler.rollbackTransaction();
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedInsertOrganizationStatement);
            StatementBuilder.closedPreparedStatement(preparedInsertCoordinatesStatement);
            StatementBuilder.closedPreparedStatement(preparedInsertAddressStatement);
            databaseHandler.stopTransaction();
        }
    }

    public void updateOrganizationById(int organizationId, OrganizationRaw organizationRaw) throws HandlingDatabaseException {
        PreparedStatement preparedUpdateOrganizationNameByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationTurnoverByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationFullNameByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationEmployeesCountByIdStatement = null;
        PreparedStatement preparedUpdateOrganizationTypeByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByOrganizationIdStatement = null;
        PreparedStatement preparedUpdateAddressByIdStatement = null;
        try {
            databaseHandler.startTransaction();
            databaseHandler.setSavepoint();

            preparedUpdateOrganizationNameByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_NAME_BY_ID, false);
            preparedUpdateOrganizationTurnoverByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_TURNOVER_BY_ID, false);
            preparedUpdateOrganizationFullNameByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_FULL_NAME_BY_ID, false);
            preparedUpdateOrganizationEmployeesCountByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_EMPLOYEE_BY_ID, false);
            preparedUpdateOrganizationTypeByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_TYPE_BY_ID, false);
            preparedUpdateCoordinatesByOrganizationIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_COORDINATES_BY_ORGANIZATION_ID, false);
            preparedUpdateAddressByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.UPDATE_ORGANIZATION_ADDRESS_BY_ID, false);

            if (organizationRaw.getName() != null) {
                preparedUpdateOrganizationNameByIdStatement.setString(1, organizationRaw.getName());
                preparedUpdateOrganizationNameByIdStatement.setInt(2, organizationId);
                if (preparedUpdateOrganizationNameByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (organizationRaw.getCoordinates() != null) {
                preparedUpdateCoordinatesByOrganizationIdStatement.setLong(1, organizationRaw.getCoordinates().getX());
                preparedUpdateCoordinatesByOrganizationIdStatement.setLong(2, organizationRaw.getCoordinates().getY());
                preparedUpdateCoordinatesByOrganizationIdStatement.setInt(3, organizationId);
                if (preparedUpdateCoordinatesByOrganizationIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (organizationRaw.getAnnualTurnover() != null) {
                preparedUpdateOrganizationTurnoverByIdStatement.setFloat(1, organizationRaw.getAnnualTurnover());
                preparedUpdateOrganizationTurnoverByIdStatement.setInt(2, organizationId);
                if (preparedUpdateOrganizationTurnoverByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (organizationRaw.getFullName() != null) {
                preparedUpdateOrganizationFullNameByIdStatement.setString(1, organizationRaw.getFullName());
                preparedUpdateOrganizationFullNameByIdStatement.setInt(2, organizationId);
                if (preparedUpdateOrganizationFullNameByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (organizationRaw.getEmployeesCount() != null) {
                preparedUpdateOrganizationEmployeesCountByIdStatement.setInt(1, organizationRaw.getEmployeesCount());
                preparedUpdateOrganizationEmployeesCountByIdStatement.setInt(2, organizationId);
                if (preparedUpdateOrganizationEmployeesCountByIdStatement.executeUpdate() == 0)
                    throw new SQLException();
            }
            if (organizationRaw.getType() != null) {
                preparedUpdateOrganizationTypeByIdStatement.setString(1, organizationRaw.getType().toString());
                preparedUpdateOrganizationTypeByIdStatement.setInt(2, organizationId);
                if (preparedUpdateOrganizationTypeByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (organizationRaw.getAddress() != null) {
                preparedUpdateAddressByIdStatement.setString(1, organizationRaw.getAddress().getStreet());
                preparedUpdateAddressByIdStatement.setInt(2, getAddressIdById(organizationId));
                if (preparedUpdateAddressByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            databaseHandler.commitTransaction();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing a group of requests to update an object!");
            databaseHandler.rollbackTransaction();
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationNameByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationTurnoverByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationFullNameByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationEmployeesCountByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateOrganizationTypeByIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateCoordinatesByOrganizationIdStatement);
            StatementBuilder.closedPreparedStatement(preparedUpdateAddressByIdStatement);
            databaseHandler.stopTransaction();
        }
    }

    public void deleteOrganizationById(int organizationId) throws HandlingDatabaseException {
        PreparedStatement preparedDeleteOrganizationByIdStatement = null;
        try {
            preparedDeleteOrganizationByIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.DELETE_ORGANIZATION_BY_ID, false);
            preparedDeleteOrganizationByIdStatement.setInt(1, organizationId);
            if (preparedDeleteOrganizationByIdStatement.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the DELETE_ORGANIZATION_BY_ID request!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedDeleteOrganizationByIdStatement);
        }
    }

    public boolean checkOrganizationUserById(int organizationId, User user) throws HandlingDatabaseException {
        PreparedStatement preparedSelectOrganizationIdAndUserIdStatement = null;
        try {
            preparedSelectOrganizationIdAndUserIdStatement = StatementBuilder.buildPreparedStatement(databaseConnector.getConnection(), DatabaseConstants.SELECT_ORGANIZATION_BY_ID_AND_USER_ID, false);
            preparedSelectOrganizationIdAndUserIdStatement.setInt(1, organizationId);
            preparedSelectOrganizationIdAndUserIdStatement.setInt(2, databaseUserManager.selectUserIdByUsername(user));
            ResultSet resultSet = preparedSelectOrganizationIdAndUserIdStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            ConsolePrinter.printError("An error occurred while executing the SELECT_ORGANIZATION_BY_ID_AND_USER_ID query!");
            throw new HandlingDatabaseException();
        } finally {
            StatementBuilder.closedPreparedStatement(preparedSelectOrganizationIdAndUserIdStatement);
        }
    }

}
