package dataaccess;

import java.sql.SQLException;

public class MySQLUserDAO {
    private static String TABLE_NAME = "user";

    public MySQLUserDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String[] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    " username VARCHAR(200) PRIMARY KEY," +
                    " password VARCHAR(200) NOT NULL," +
                    " email VARCHAR(200) NOT NULL" +
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
}
