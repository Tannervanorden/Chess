package dataaccess;

import com.google.gson.Gson;
import chess.ChessGame;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLGameDAO {
    private static String TABLE_NAME = "game";
    private Gson gson = new Gson();

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    private final String[] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "  `gameID` INT AUTO_INCREMENT PRIMARY KEY," +
                    "  `whiteUsername` VARCHAR(100) NULL," +
                    "  `blackUsername` VARCHAR(100) NULL," +
                    "  `gameName` VARCHAR(100) NULL," +
                    "  `game` TEXT NULL" +
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

    public GameData getGame(int gameID) throws DataAccessException {
        String query = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM " + TABLE_NAME + " WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, gameID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("gameID");
                    String whiteUsername = resultSet.getString("whiteUsername");
                    String blackUsername = resultSet.getString("blackUsername");
                    String gameName = resultSet.getString("gameName");
                    String gameJson = resultSet.getString("game");
                    ChessGame game = gson.fromJson(gameJson, ChessGame.class);  // Deserializing the game
                    return new GameData(id, whiteUsername, blackUsername, gameName, game);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get game: " + ex.getMessage());
        }
    }

    public void updateGame(int id, GameData game) throws DataAccessException {
        String query = "UPDATE " + TABLE_NAME + " SET whiteUsername = ?, blackUsername = ?, gameName = ?, game = ? WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, game.whiteUsername());
            preparedStatement.setString(2, game.blackUsername());
            preparedStatement.setString(3, game.gameName());
            String gameJson = gson.toJson(game.game());
            preparedStatement.setString(4, gameJson);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error updating game: " + ex.getMessage());
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
