package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public UserData getUser(String username) throws DataAccessException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setString(1, username);
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));

            }
        }
        } catch (SQLException ex) {
            throw new DataAccessException("Error" + ex.getMessage());
        }
        return null;
    }

    public void addUser(UserData user) throws DataAccessException {
        String query = "INSERT INTO " + TABLE_NAME + "(username, password, email) VALUES (?,?,?)";
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.username());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.email());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error: can't add user" + ex.getMessage());
        }
    }
}
