package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public boolean validateToken(String token) throws DataAccessException {
        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE authToken = ?";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, token);
            try (var resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to validate token: " + ex.getMessage());
        }
    }

    public void addAuth(String token, AuthData authData) throws DataAccessException {
        String query = "INSERT INTO " + TABLE_NAME + " (authToken, username) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, token);
            statement.setString(2, authData.username());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to add auth token: " + ex.getMessage());
        }
    }
}
