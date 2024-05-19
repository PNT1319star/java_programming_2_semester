package database;

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
    public static final String USER_TABLE_ROLE_ID_COLUMN = "role_id";
    //ADDRESS_TABLE column names
    public static final String ADDRESS_TABLE_ID_COLUMN = "id";
    public static final String ADDRESS_TABLE_STREET_COLUMN = "street";
    //COORDINATES_TABLE column names
    public static final String COORDINATES_TABLE_ID_COLUMN = "id";
    public static final String COORDINATES_TABLE_X_COLUMN = "x";
    public static final String COORDINATES_TABLE_Y_COLUMN = "y";
    // ORGANIZATION_TABLE column names
    public static final String ORGANIZATION_TABLE_ID_COLUMN = "id";
    public static final String ORGANIZATION_TABLE_NAME_COLUMN = "name";
    public static final String ORGANIZATION_TABLE_TIME_COLUMN = "creation_time";
    public static final String ORGANIZATION_TABLE_TURNOVER_COLUMN = "annual_turnover";
    public static final String ORGANIZATION_TABLE_FULL_NAME_COLUMN = "full_name";
    public static final String ORGANIZATION_TABLE_COORDINATES_ID = "coordinates_id";
    public static final String ORGANIZATION_TABLE_EMPLOYEE_COLUMN = "employee_count";
    public static final String ORGANIZATION_TABLE_TYPE_COLUMN = "organization_type";
    public static final String ORGANIZATION_TABLE_ADDRESS_ID_COLUMN = "address_id";
    public static final String ORGANIZATION_TABLE_USER_ID_COLUMN = "user_id";
    // ORGANIZATION_TABLE
    public static final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM " + ORGANIZATION_TABLE;
    public static final String INSERT_ORGANIZATION = "INSERT INTO " +
            ORGANIZATION_TABLE + " (" +
            ORGANIZATION_TABLE_NAME_COLUMN + ", " +
            ORGANIZATION_TABLE_TIME_COLUMN + ", " +
            ORGANIZATION_TABLE_TURNOVER_COLUMN + ", " +
            ORGANIZATION_TABLE_FULL_NAME_COLUMN + ", " +
            ORGANIZATION_TABLE_COORDINATES_ID + ", " +
            ORGANIZATION_TABLE_EMPLOYEE_COLUMN + ", " +
            ORGANIZATION_TABLE_TYPE_COLUMN + ", " +
            ORGANIZATION_TABLE_ADDRESS_ID_COLUMN + ", " +
            ORGANIZATION_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?::organization_type, ?, ?)";
    public static final String UPDATE_ORGANIZATION_NAME_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_TIME_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_TIME_COLUMN + " = ?" + " WHERE " +
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
            ORGANIZATION_TABLE_TYPE_COLUMN + " = ? :: organization_type " + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_ADDRESS_BY_ID = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_ADDRESS_ID_COLUMN + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String UPDATE_ORGANIZATION_COORDINATES = "UPDATE " + ORGANIZATION_TABLE + " SET " +
            ORGANIZATION_TABLE_COORDINATES_ID + " = ?" + " WHERE " +
            ORGANIZATION_TABLE_ID_COLUMN + " = ?";
    public static final String DELETE_ORGANIZATION_BY_ID_AND_USER_ID = "DELETE FROM " + ORGANIZATION_TABLE +
            " WHERE " + ORGANIZATION_TABLE_ID_COLUMN + " = ? AND " + ORGANIZATION_TABLE_USER_ID_COLUMN + " = ?";
    public static final String DELETE_ORGANIZATION_BY_USER_ID = "DELETE FROM " + ORGANIZATION_TABLE +
            " WHERE " + ORGANIZATION_TABLE_USER_ID_COLUMN + " = ?";

    //USER_TABLE
    public static final String SELECT_USER_BY_ID = "SELECT * FROM " + USER_TABLE + " WHERE " +
            USER_TABLE_ID_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + USER_TABLE + " WHERE " +
            USER_TABLE_USERNAME_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM " + USER_TABLE + " WHERE " +
            USER_TABLE_USERNAME_COLUMN + " = ? AND " + USER_TABLE_PASSWORD_COLUMN + " = ?";
    public static final String INSERT_USER = "INSERT INTO " +
            USER_TABLE + " (" +
            USER_TABLE_USERNAME_COLUMN + ", " +
            USER_TABLE_PASSWORD_COLUMN + ", " +
            USER_TABLE_ROLE_ID_COLUMN + ") VALUES (?, ?, ?)";
    public static final String UPDATE_ROLE_ID_BY_USERNAME = "UPDATE " + USER_TABLE + " SET " + USER_TABLE_ROLE_ID_COLUMN
            + " = ? WHERE " + USER_TABLE_USERNAME_COLUMN + " = ?";
    // COORDINATES_TABLE
    public static final String SELECT_ALL_COORDINATES = "SELECT * FROM " + COORDINATES_TABLE;
    public static final String SELECT_COORDINATES_BY_COORDINATE_ID = SELECT_ALL_COORDINATES +
            " WHERE " + COORDINATES_TABLE_ID_COLUMN + " = ?";
    public static final String INSERT_COORDINATES = "INSERT INTO " +
            COORDINATES_TABLE + " (" +
            COORDINATES_TABLE_X_COLUMN + ", " +
            COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?) ON CONFLICT (x,y) DO NOTHING RETURNING id ";
    public static final String SELECT_COORDINATE_ID_BY_X_AND_Y = "SELECT " + COORDINATES_TABLE_ID_COLUMN + " FROM " +
            COORDINATES_TABLE + " WHERE " + COORDINATES_TABLE_X_COLUMN + " = ? AND " + COORDINATES_TABLE_Y_COLUMN + " = ?";

    //ADDRESS_TABLE
    public static final String SELECT_ALL_ADDRESSES = "SELECT * FROM " + ADDRESS_TABLE;
    public static final String SELECT_ADDRESS_BY_ID = SELECT_ALL_ADDRESSES +
            " WHERE " + ADDRESS_TABLE_ID_COLUMN + " = ?";
    public static final String SELECT_ADDRESS_ID_BY_ADDRESS = "SELECT id FROM " + ADDRESS_TABLE +
            " WHERE " + ADDRESS_TABLE_STREET_COLUMN + " = ?";
    public static final String INSERT_ADDRESS = "INSERT INTO " +
            ADDRESS_TABLE + " (" +
            ADDRESS_TABLE_STREET_COLUMN + ") VALUES (?) ON CONFLICT (street) DO NOTHING RETURNING id";
    public static final String SELECT_ROLE_ID_BY_ROLE = "SELECT id FROM ROLES WHERE role = ?";
    // SESSIONS_TABLE
    public static final String SELECT_SESSION_ID_BY_USER_ID = "SELECT id FROM SESSIONS WHERE user_id = ?";
    public static final String SELECT_USER_ID_BY_SESSION_ID = "SELECT user_id FROM SESSIONS WHERE id = ?";
    public static final String INSERT_NEW_USER_INTO_SESSION_TABLE = "INSERT INTO SESSIONS (id, user_id, created_at, expires_at)" +
            " VALUES (?, ?, ?, ?)";
    public static final String SELECT_FUNCTIONS_BY_SESSION_ID = "SELECT f.function " +
            "FROM sessions AS s " +
            "JOIN users AS u ON s.user_id = u.id " +
            "JOIN roles AS r ON u.role_id = r.id " +
            "JOIN role_functions AS rf ON r.id = rf.role_id " +
            "JOIN functions AS f ON rf.function_id = f.id " +
            "WHERE s.id = ?";
}
