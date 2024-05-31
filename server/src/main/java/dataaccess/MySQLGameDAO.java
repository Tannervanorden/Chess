package dataaccess;

import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLGameDAO {
    private static String TABLE_NAME = "game";

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String[] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "  `gameID` INT AUTO_INCREMENT PRIMARY KEY," +
                    "  `whiteUsername` VARCHAR(100) NOT NULL," +
                    "  `blackUsername` VARCHAR(100) NOT NULL," +
                    "   `gameName` VARCHAR(100) NOT NULL" +
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

    public void addGame(GameData game) throws DataAccessException {
        String query = "INSERT INTO " + TABLE_NAME + " (whiteUsername, blackUsername, gameName) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, game.whiteUsername());
            preparedStatement.setString(2, game.blackUsername());
            preparedStatement.setString(3, game.gameName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error adding game: " + ex.getMessage());
        }
    }
}
