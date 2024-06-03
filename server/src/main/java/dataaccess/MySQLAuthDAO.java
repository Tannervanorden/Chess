package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLAuthDAO extends GenericDAO {
    private static String tableName = "auth";

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase(createStatements);
    }

    final String [] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    " authToken VARCHAR(200) PRIMARY KEY," +
                    " username VARCHAR(200) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
    };

    public boolean validateToken(String token) throws DataAccessException {
        String query = "SELECT 1 FROM " + tableName + " WHERE authToken = ?";
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
        String query = "INSERT INTO " + tableName + " (authToken, username) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, token);
            statement.setString(2, authData.username());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to add auth token: " + ex.getMessage());
        }
    }

    public Map<String, AuthData> getAuth() throws DataAccessException {
        Map<String, AuthData> authData = new HashMap<>();
        String query = "SELECT authToken, username FROM " + tableName;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String token = resultSet.getString("authToken");
                String username = resultSet.getString("username");
                authData.put(token, new AuthData(token, username));
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get auth data: " + ex.getMessage());
        }
        return authData;
    }


    public AuthData removeAuth(String token) throws DataAccessException {
        String query = "SELECT username FROM " + tableName + " WHERE authToken = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement selectStatement = conn.prepareStatement(query)) {
            selectStatement.setString(1, token);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    AuthData authData = new AuthData(token, username);
                    String deleteQuery = "DELETE FROM " + tableName + " WHERE authToken = ?";
                    try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                        deleteStatement.setString(1, token);
                        deleteStatement.executeUpdate();
                    }
                    return authData;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to remove auth token: " + ex.getMessage());
        }
        return null;
    }

    public String getUsername(String token) throws DataAccessException {
        String query = "SELECT username FROM " + tableName + " WHERE authToken = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, token);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get auth token: " + ex.getMessage());
        }
    }
}
