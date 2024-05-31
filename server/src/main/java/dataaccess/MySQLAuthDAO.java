package dataaccess;

import java.sql.SQLException;

public class MySQLAuthDAO {
    private static String TABLE_NAME = "auth";

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String [] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    " authToken VARCHAR(200) PRIMARY KEY," +
                    " username VARCHAR(200) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to configure database: " + ex.getMessage());
        }
    }
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String clearTableSQL = "TRUNCATE TABLE " + TABLE_NAME;
            try (var preparedStatement = conn.prepareStatement(clearTableSQL)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to clear database: " + ex.getMessage());
        }
    }
}
