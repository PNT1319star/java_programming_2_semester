package database;

//All syntax in PosgreSQL
public class DatabaseConstants {
    // Table names
    public static final String USER_TABLE = "USERS";
    public static final String ORGANIZATION_TABLE = "ORGANIZATIONS";
    public static final String ADDRESS_TABLE = "ADDRESSES";
    public static final String COORDINATES_TABLE = "COORDINATES";
    // USER_TABLE column names
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";
    //ADDRESS_TABLE column names
    public static final String ADDRESS_TABLE_ID_COLUMN = "id";
    public static final String ADDRESS_TABLE_STREET_COLUMN = "street";
    //COORDINATES_TABLE column names
    public static final String COORDINATES_TABLE_ID_COLUMN = "id";
    public static final String COORDINATES_TABLE_X_COLUMN = "x";
    public static final String COORDINATES_TABLE_Y_COLUMN = "y";
    public static final String COORDINATES_TABLE_ORGANIZATION_ID_COLUMN = "organization_id";
    // ORGANIZATION_TABLE column names
    public static final String ORGANIZATION_TABLE_ID_COLUMN = "id";
    public static final String ORGANIZATION_TABLE_NAME_COLUMN = "name";
    public static final String ORGANIZATION_TABLE_TIME_COLUMN = "creation_time";
    public static final String ORGANIZATION_TABLE_TURNOVER_COLUMN = "annual_turnover";
    public static final String ORGANIZATION_TABLE_FULL_NAME_COLUMN = "full_name";
    public static final String ORGANIZATION_TABLE_EMPLOYEE_COLUMN = "employee_count";
    public static final String ORGANIZATION_TABLE_TYPE_COLUMN = "organization_type";
    public static final String ORGANIZATION_TABLE_ADDRESS_ID_COLUMN = "address_id";
    public static final String ORGANIZATION_TABLE_USER_ID_COLUMN = "user_id";
    // ORGANIZATION_TABLE
    public static final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM " + ORGANIZATION_TABLE;
    public static final String SELECT_ORGANIZATION_BY_ID = SELECT_ALL_ORGANIZATIONS + "WHERE " + ORGANIZATION_TABLE_ID_COLUMN + " = ?";// for commands "remove_by_id", "update"
    public static final String SELECT_ORGANIZATION_BY_FULL_NAME = SELECT_ALL_ORGANIZATIONS + "WHERE " + ORGANIZATION_TABLE_FULL_NAME_COLUMN + " = ?";// for commands "filter_starts_with_full_name"
    public static final String SELECT_ORGANIZATION_BY_ID_AND_USER_ID = SELECT_ORGANIZATION_BY_ID + " AND " + USER_TABLE_ID_COLUMN + " = ?";
    public static final String INSERT_ORGANIZATION = "INSERT INTO " +
            ORGANIZATION_TABLE + " (" +
            ORGANIZATION_TABLE_NAME_COLUMN + ", " +
            ORGANIZATION_TABLE_TIME_COLUMN + ", " +
            ORGANIZATION_TABLE_TURNOVER_COLUMN + ", " +
            ORGANIZATION_TABLE_FULL_NAME_COLUMN + ", " +
            ORGANIZATION_TABLE_EMPLOYEE_COLUMN + ", " +
            ORGANIZATION_TABLE_TYPE_COLUMN + ", " +
            ORGANIZATION_TABLE_ADDRESS_ID_COLUMN + ", " +
            ORGANIZATION_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?::organization_type, ?, ?)";
    public static final String UPDATE_ORGANIZATION_NAME_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_TURNOVER_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_TURNOVER_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_FULL_NAME_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_FULL_NAME_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_EMPLOYEE_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_EMPLOYEE_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_TYPE_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_TYPE_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_ADDRESS_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_ADDRESS_ID_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String DELETE_ORGANIZATION_BY_ID = "DELETE FROM " + ORGANIZATION_TABLE +
            " WHERE " + ORGANIZATION_TABLE_ID_COLUMN + " = ?";

    //USER_TABLE
    public static final String SELECT_USER_BY_ID = "SELECT * FROM " + USER_TABLE + " WHERE " +
            USER_TABLE_ID_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + USER_TABLE + " WHERE " +
            USER_TABLE_USERNAME_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
            USER_TABLE_PASSWORD_COLUMN + " = ?";
    public static final String INSERT_USER = "INSERT INTO " +
            USER_TABLE + " (" +
            USER_TABLE_USERNAME_COLUMN + ", " +
            USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?)";

    // COORDINATES_TABLE
    public static final String SELECT_ALL_COORDINATES = "SELECT * FROM " + COORDINATES_TABLE;
    public static final String SELECT_COORDINATES_BY_ORGANIZATION_ID = SELECT_ALL_COORDINATES +
            " WHERE " + ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String INSERT_COORDINATES = "INSERT INTO " +
            COORDINATES_TABLE + " (" +
            COORDINATES_TABLE_ORGANIZATION_ID_COLUMN + ", " +
            COORDINATES_TABLE_X_COLUMN + ", " +
            COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?, ?)";
    public static final String UPDATE_COORDINATES_BY_ORGANIZATION_ID = "UPDATE " + COORDINATES_TABLE + " SET " +
            COORDINATES_TABLE_X_COLUMN + " = ?, " +
            COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            COORDINATES_TABLE_ORGANIZATION_ID_COLUMN + " = ?";

    //ADDRESS_TABLE
    public static final String SELECT_ALL_ADDRESSES = "SELECT * FROM " + ADDRESS_TABLE;
    public static final String SELECT_ADDRESS_BY_ID = SELECT_ALL_ADDRESSES +
            " WHERE " + ADDRESS_TABLE_ID_COLUMN + " = ?";
    public static final String INSERT_ADDRESS = "INSERT INTO " +
            ADDRESS_TABLE + " (" +
            ADDRESS_TABLE_STREET_COLUMN + ") VALUES (?)";
    public static final String UPDATE_ADDRESS_BY_ID = "UPDATE " + ADDRESS_TABLE + " SET " +
            ADDRESS_TABLE_STREET_COLUMN + " = ? " + " WHERE " +
            ADDRESS_TABLE_ID_COLUMN + " = ?";

}
